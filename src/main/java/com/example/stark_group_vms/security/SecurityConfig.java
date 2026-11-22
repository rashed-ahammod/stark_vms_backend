package com.example.stark_group_vms.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final SecurityProperties properties;
    private final SecretKey secretKey;

    public SecurityConfig(SecurityProperties properties, Environment environment) {
        this.properties = properties;
        this.secretKey = resolveSecretKey(properties, environment);
    }

    /**
     * Built once and reused, so the encoder and the decoder always share the same
     * key. In production a missing secret is fatal; in development a random one is
     * generated so the app still starts, at the cost of invalidating issued tokens
     * on every restart.
     */
    private static SecretKey resolveSecretKey(SecurityProperties properties, Environment environment) {
        String secret = properties.getJwtSecret();
        boolean usable = secret != null && secret.getBytes(StandardCharsets.UTF_8).length >= 32;

        if (usable) {
            return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        }

        if (List.of(environment.getActiveProfiles()).contains("prod")) {
            throw new IllegalStateException(
                    "JWT_SECRET must be set to at least 32 characters in the prod profile.");
        }

        byte[] generated = new byte[32];
        new SecureRandom().nextBytes(generated);
        log.warn("JWT_SECRET is not set (or is shorter than 32 characters) - generated a random "
                + "development secret. Tokens will stop working when the application restarts. "
                + "Set JWT_SECRET to keep sessions stable.");
        return new SecretKeySpec(generated, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS is configured here rather than with @CrossOrigin on the controller:
     * the security filter chain rejects the pre-flight OPTIONS request before it
     * ever reaches a controller, so annotation-based CORS stops working once
     * Spring Security is on the classpath.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // "*" stays available for local development; production sets
        // CORS_ALLOWED_ORIGINS to the Flutter Web domain(s).
        // Patterns are used rather than setAllowedOrigins so an entry may carry a
        // wildcard, e.g. "http://localhost:*" for a Flutter dev server whose port
        // changes between runs. An entry without a wildcard still matches exactly.
        List<String> origins = properties.getAllowedOrigins();
        if (origins == null || origins.isEmpty()) {
            config.setAllowedOriginPatterns(List.of("*"));
        } else {
            config.setAllowedOriginPatterns(origins);
        }
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        // Render polls this to decide whether the instance is live.
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        // Without this, the ERROR dispatch is itself authenticated and
                        // every 404/405/500 is reported to the client as a 401.
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                // The entry point is set on the resource server as well as on
                // exceptionHandling: the resource server installs its own otherwise,
                // which answers with an empty body instead of the documented envelope.
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt.decoder(jwtDecoder()))
                        .authenticationEntryPoint(this::unauthorized)
                        .accessDeniedHandler(this::forbidden))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(this::unauthorized)
                        .accessDeniedHandler(this::forbidden));

        return http.build();
    }

    private void unauthorized(jakarta.servlet.http.HttpServletRequest request,
                              jakarta.servlet.http.HttpServletResponse response,
                              RuntimeException exception) throws java.io.IOException {
        writeError(response, HttpStatus.UNAUTHORIZED,
                "Authentication required. Send a valid Bearer token.");
    }

    private void forbidden(jakarta.servlet.http.HttpServletRequest request,
                           jakarta.servlet.http.HttpServletResponse response,
                           RuntimeException exception) throws java.io.IOException {
        writeError(response, HttpStatus.FORBIDDEN,
                "You do not have permission to perform this action.");
    }

    /** Keeps 401/403 bodies in the same shape as every other error in API.md. */
    private void writeError(jakarta.servlet.http.HttpServletResponse response,
                            HttpStatus status, String message) throws java.io.IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format(
                "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}",
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), message));
        response.getWriter().flush();
    }
}
