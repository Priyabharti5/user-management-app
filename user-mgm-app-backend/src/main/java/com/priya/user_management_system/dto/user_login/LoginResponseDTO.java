package com.priya.user_management_system.dto.user_login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for sending login response back to the client after successful authentication.
 *
 * This class contains:
 * - token    : JWT authentication token used for accessing secured APIs.
 * - type     : Token type used in Authorization header (e.g., Bearer).
 * - username : Username of the authenticated user.
 *
 * This DTO is returned by the authentication controller after successful login.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;

    private String type;

    private String username;
}
