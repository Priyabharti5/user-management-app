package com.priya.user_management_system.dto.add_user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddUserResponseDTO
 *
 * This DTO represents the response returned after creating a new user.
 * It includes:
 * - status : Indicates whether the user creation was successful or failed.
 * - userId : The unique ID of the newly created user.
 *
 * This class is used in the Add User API response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserResponseDTO {

    private String status;

    private Integer userId;
}
