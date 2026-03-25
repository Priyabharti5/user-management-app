package com.priya.user_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for sending user details in API responses.
 *
 * This class represents the user information that will be returned to the client.
 * It does not include sensitive information such as passwords.
 *
 * It includes:
 * - username
 * - firstName
 * - lastName
 * - email
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String username;

    private String firstName;

    private String lastName;

    private String email;
}
