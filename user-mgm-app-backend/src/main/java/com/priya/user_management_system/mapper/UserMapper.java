package com.priya.user_management_system.mapper;

import com.priya.user_management_system.dto.add_user.AddUserRequestDTO;
import com.priya.user_management_system.dto.UserResponseDTO;
import com.priya.user_management_system.entity.User;

/**
 * Utility mapper class for converting between User entity and DTO objects.
 */
public final class UserMapper {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private UserMapper(){
        throw new UnsupportedOperationException("Utility class - can not be instantiated");
    }

    public static User toEntity(AddUserRequestDTO requestDTO){
        return User.builder()
                .username(requestDTO.getUsername())
                .firstName(requestDTO.getFirstName())
                .lastName(requestDTO.getLastName())
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .build();
    }

    public static UserResponseDTO toDTO(User user){
        return UserResponseDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
