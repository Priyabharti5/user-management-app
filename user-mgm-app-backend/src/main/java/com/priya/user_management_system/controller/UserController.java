package com.priya.user_management_system.controller;

import com.priya.user_management_system.dto.add_user.AddUserResponseDTO;
import com.priya.user_management_system.dto.add_user.AddUserRequestDTO;
import com.priya.user_management_system.dto.UserResponseDTO;
import com.priya.user_management_system.dto.general_response.ResponseDTO;
import com.priya.user_management_system.dto.search.UserSearchRequestDTO;
import com.priya.user_management_system.entity.User;
import com.priya.user_management_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 * Exposes APIs-
 *          - Add a new user
 *          - Search Users based on given filters (username, firstName, lastName, email)
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Add User API
     *
     * @param requestDTO
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<AddUserResponseDTO> addUser(@Valid @RequestBody AddUserRequestDTO requestDTO) {
        log.info("[AddUser] Received request to create user with userName: {}", requestDTO.getUsername());
        AddUserResponseDTO response = userService.addUser(requestDTO);
        log.info("[AddUser] User created successfully with userId: {}", response.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Search Users API
     *
     * @param requestDTO
     * @return ResponseEntity<UserResponseDTO>
     */
    @PostMapping("/search")
    public ResponseEntity<List<UserResponseDTO>> searchUsers(@RequestBody UserSearchRequestDTO requestDTO) {
        log.info("[SearchUser] Search request received with data: {}", requestDTO);
        List<UserResponseDTO> response = userService.searchUsers(requestDTO);
        if (response.isEmpty()) {
            log.warn("[SearchUser] No users found for criteria: {}", requestDTO);
        } else {
            log.info("[SearchUser] Found {} user(s) matching criteria", response.size());
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String username){
        ResponseDTO responseDTO = userService.deleteUser(username);

        return ResponseEntity.ok(responseDTO);
    }

}
