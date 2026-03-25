package com.priya.user_management_system.repository;

import com.priya.user_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity.
 * Provides database operations such as CRUD, find by username/email,
 * and dynamic user search using optional filters.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Searches users based on optional filters.
     */
    @Query(value = """
    SELECT * FROM users
    WHERE (:username IS NULL OR username ILIKE CONCAT('%', :username, '%'))
    AND (:firstName IS NULL OR first_name ILIKE CONCAT('%', :firstName, '%'))
    AND (:lastName IS NULL OR last_name ILIKE CONCAT('%', :lastName, '%'))
    AND (:email IS NULL OR email ILIKE CONCAT('%', :email, '%'))
    """, nativeQuery = true)
    List<User> searchUsers(
            @Param("username") String username,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email
    );
}
