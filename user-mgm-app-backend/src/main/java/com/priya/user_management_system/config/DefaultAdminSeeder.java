package com.priya.user_management_system.config;

import com.priya.user_management_system.entity.User;
import com.priya.user_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * DefaultAdminSeeder
 *
 * This class runs automatically when the Spring Boot application starts.
 * It ensures that a default admin user is always present in the system.
 *
 * Logic:
 * - Checks if a user with username "admin" already exists in the database.
 * - If the admin user exists → do nothing.
 * - If the admin user does NOT exist → create a default admin user.
 *
 * Default Admin Credentials:
 * Username: admin
 * Password: admin123
 *
 * Purpose:
 * - Ensures there is always at least one user who can log in.
 * - Useful for first-time application setup.
 * - Prevents system from having no users after fresh database setup.
 *
 * This class implements CommandLineRunner, so it runs automatically
 * after the Spring Boot application context is loaded.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DefaultAdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String defaultUsername = "admin";
        String defaultPassword = "admin123";

        log.info("Checking default admin user...");

        userRepository.findByUsername(defaultUsername).ifPresentOrElse(
                // If user with username="admin" present
                existingUser -> {
                    log.info("Default admin user already exists with username: {}", defaultUsername);
                },

                // If user with username="admin" not present
                () -> {
                    log.warn("Default admin user not found. Creating default admin user...");

                    User admin = User.builder()
                            .username(defaultUsername)
                            .firstName("Admin")
                            .lastName("User")
                            .email("admin@gmail.com")
                            .password(passwordEncoder.encode(defaultPassword))
                            .build();

                    userRepository.save(admin);

                    log.warn("Default admin user created -> username: admin | password: admin123");
                }
        );

        log.info("Default admin check completed.");
    }
}