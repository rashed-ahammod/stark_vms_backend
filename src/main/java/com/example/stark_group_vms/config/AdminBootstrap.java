package com.example.stark_group_vms.config;

import com.example.stark_group_vms.entity.User;
import com.example.stark_group_vms.repository.UserRepository;
import com.example.stark_group_vms.security.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Creates the initial administrator from ADMIN_USERNAME / ADMIN_PASSWORD when no
 * account with that username exists yet. The password is read from the
 * environment, stored only as a BCrypt hash, and never written to the log.
 */
@Configuration
public class AdminBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);

    @Bean
    public CommandLineRunner createInitialAdmin(UserRepository userRepository,
                                                PasswordEncoder passwordEncoder,
                                                SecurityProperties properties) {
        return args -> {
            String username = properties.getAdminUsername();
            String password = properties.getAdminPassword();

            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                if (userRepository.count() == 0) {
                    log.warn("No users exist and ADMIN_USERNAME/ADMIN_PASSWORD are not set - "
                            + "nobody can log in. See API.md section 7.");
                }
                return;
            }

            if (userRepository.existsByUsernameIgnoreCase(username.trim())) {
                return;
            }

            User admin = new User();
            admin.setUsername(username.trim());
            admin.setPassword(passwordEncoder.encode(password));
            admin.setFullName(properties.getAdminFullName());
            userRepository.save(admin);

            log.info("Created initial administrator account '{}'", admin.getUsername());
        };
    }
}
