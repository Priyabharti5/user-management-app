package com.priya.user_management_system.service.impl;

import com.priya.user_management_system.dto.user_login.LoginRequestDTO;
import com.priya.user_management_system.dto.user_login.LoginResponseDTO;
import com.priya.user_management_system.exception.InvalidCredentialsException;
import com.priya.user_management_system.security.AuthCustomUserDetails;
import com.priya.user_management_system.security.JWTUtil;
import com.priya.user_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * Authenticates the user using username and password.
 * If authentication is successful, a JWT token is generated and returned.
 * If authentication fails, an InvalidCredentialsException is thrown.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    /**
     * Authenticates a user with the given credentials and returns a JWT token on success.
     * <p>
     * Notes:
     * - Logs do not contain sensitive data like passwords.
     * - On invalid credentials, a custom InvalidCredentialsException is thrown.
     */
    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        log.debug("Login attempt for user [{}]", loginRequestDTO.getUsername());

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

            AuthCustomUserDetails principal = (AuthCustomUserDetails) auth.getPrincipal();

            String role = principal.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("ROLE_USER");

            String token = jwtUtil.generateToken(principal.getUsername());

            log.info("User [{}] logged in successfully with role [{}]", principal.getUsername(), role);

            return new LoginResponseDTO(token, "Bearer", principal.getUsername());

        } catch (BadCredentialsException e) {
            log.warn("Login failed - invalid credentials for user [{}]", loginRequestDTO.getUsername());
            throw new InvalidCredentialsException("Invalid username or password");
        } catch (AuthenticationException e) {
            log.warn("Login failed - authentication error for user [{}]: {}", loginRequestDTO.getUsername(), e.getMessage());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
