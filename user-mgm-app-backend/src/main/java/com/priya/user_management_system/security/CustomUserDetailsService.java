package com.priya.user_management_system.security;

import com.priya.user_management_system.entity.User;
import com.priya.user_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 *
 * Responsible for loading user from the database
 * during authentication and JWT token validation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Fetches user details from the database using the provided username.
     *
     * @param username the username entered by the user
     * @return UserDetails object containing authentication information
     * @throws UsernameNotFoundException if no matching user is found
     */
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username, "Username must not be null");
        log.debug("[Auth] Loading user details for username: {}", username);

        // Look up user in the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("[Auth] User not found: {}", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });

        log.info("[Auth] User found and authenticated: {}", username);

        // Wrap entity into a Spring Security compatible UserDetails object
        return new AuthCustomUserDetails(user);
    }
}
