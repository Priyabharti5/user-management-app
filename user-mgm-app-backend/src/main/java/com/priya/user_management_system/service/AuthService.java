package com.priya.user_management_system.service;


import com.priya.user_management_system.dto.user_login.LoginRequestDTO;
import com.priya.user_management_system.dto.user_login.LoginResponseDTO;

/**
 * AuthService
 *
 * Defines authentication-related business logic.
 */
public interface AuthService {

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param requestDTO Login request containing username and password
     * @return LoginResponseDTO containing JWT token and login status
     */
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
