package com.priya.user_management_system.controller;

import com.priya.user_management_system.dto.user_login.LoginRequestDTO;
import com.priya.user_management_system.dto.user_login.LoginResponseDTO;
import com.priya.user_management_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 * Exposes APIs related to user authentication.
 * Handles login requests and issues JWT tokens.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthController {

    private final AuthService authService;

    /**
     * Authenticate user and return JWT token.
     *
     * @param loginRequestDTO
     * @return ResponseEntity<LoginResponseDTO>
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("[AuthController] Login request received for user: {}", loginRequestDTO.getUsername());
        LoginResponseDTO response = authService.login(loginRequestDTO);
        log.info("[AuthController] User '{}' logged in successfully", loginRequestDTO.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
