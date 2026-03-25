package com.priya.user_management_system.service.impl;

import com.priya.user_management_system.dto.add_user.AddUserRequestDTO;
import com.priya.user_management_system.dto.add_user.AddUserResponseDTO;
import com.priya.user_management_system.dto.UserResponseDTO;
import com.priya.user_management_system.dto.search.UserSearchRequestDTO;
import com.priya.user_management_system.entity.User;
import com.priya.user_management_system.exception.ResourceAlreadyExistsException;
import com.priya.user_management_system.mapper.UserMapper;
import com.priya.user_management_system.repository.UserRepository;
import com.priya.user_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for User operations.
 * Handles business logic for adding users and searching users.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AddUserResponseDTO addUser(AddUserRequestDTO requestDTO) {
        // when request comes for adding a user, first we should check for username and email, as they must be unique
        // Check for username
        Optional<User> existingUserWithUsername = userRepository.findByUsername(requestDTO.getUsername());
        if(existingUserWithUsername.isPresent()){
            throw new ResourceAlreadyExistsException("Username Already Exists!");
        }

        // Check for email
        Optional<User> existingUserWithEmail = userRepository.findByEmail(requestDTO.getEmail());
        if(existingUserWithEmail.isPresent()){
            throw new ResourceAlreadyExistsException("User with this email already exists!");
        }

        // Convert request DTO to entity
        User user = UserMapper.toEntity(requestDTO);

        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        user.setPassword(encodedPassword);

        // Persist user to database
        User savedUser = userRepository.save(user);
        log.info("[UserService] User successfully created with userId: {}", savedUser.getUserId());

        // Build and return response
        return AddUserResponseDTO.builder()
                .status("success")
                .userId(savedUser.getUserId())
                .build();
    }

    @Override
    public List<UserResponseDTO> searchUsers(UserSearchRequestDTO requestDTO) {
        List<User> users = userRepository.searchUsers(requestDTO.getUsername(), requestDTO.getFirstName(), requestDTO.getLastName(), requestDTO.getEmail());

        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
