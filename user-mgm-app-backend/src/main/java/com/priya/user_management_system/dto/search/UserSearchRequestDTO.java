package com.priya.user_management_system.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for user search functionality.
 * Contains fields used to filter users.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchRequestDTO {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    public String sortByField;

    public Boolean sortAscending;

}
