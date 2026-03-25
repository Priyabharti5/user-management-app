package com.priya.user_management_system.dto.add_user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**

 * DTO for Add User request.
 *
 * This class is used to receive user input data when creating a new user.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequestDTO {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Length of Username must be 3 to 50 characters")
    private String username;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, max = 50, message = "Length of first name must be 3 to 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, max = 50, message = "Length of lastt name must be 3 to 50 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotBlank(message = "password is mandatory")
    @Size(min = 8, max = 64, message = "Password length must be 8-64 characters")
    private String password;
}
