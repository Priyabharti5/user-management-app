package com.priya.user_management_system.service;

import com.priya.user_management_system.dto.add_user.AddUserRequestDTO;
import com.priya.user_management_system.dto.add_user.AddUserResponseDTO;
import com.priya.user_management_system.dto.UserResponseDTO;
import com.priya.user_management_system.dto.search.UserSearchRequestDTO;

import java.util.List;

/**
 * UserService
 *
 * Service interface that defines business operations related to users,
 * such as adding a user and searching users.
 */
public interface UserService {

    // 1. Add a new user
    AddUserResponseDTO addUser(AddUserRequestDTO requestDTO);

    // 2. Search user / users
    List<UserResponseDTO> searchUsers(UserSearchRequestDTO requestDTO);
}
