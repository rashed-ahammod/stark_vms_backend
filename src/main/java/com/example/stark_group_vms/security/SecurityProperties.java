package com.example.stark_group_vms.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Secrets and the bootstrap admin come from the environment, never from a
 * committed file. See API.md for the required variables.
 */
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private String jwtSecret;
    private long jwtExpirySeconds = 86400;
    private String adminUsername;
    private String adminPassword;
    private String adminFullName = "Administrator";
    /** Browser origins allowed to call the API. "*" permits any, for local development. */
    private List<String> allowedOrigins = List.of("*");

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long getJwtExpirySeconds() {
        return jwtExpirySeconds;
    }

    public void setJwtExpirySeconds(long jwtExpirySeconds) {
        this.jwtExpirySeconds = jwtExpirySeconds;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String getAdminFullName() {
        return adminFullName;
    }

    public void setAdminFullName(String adminFullName) {
        this.adminFullName = adminFullName;
    }
}
