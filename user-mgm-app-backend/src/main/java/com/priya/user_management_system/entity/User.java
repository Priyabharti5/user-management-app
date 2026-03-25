package com.priya.user_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * User Entity
 *
 * This entity represents the users table in the database.
 *
 * Table Details:
 * - Table Name: users
 * - Unique Constraints:
 *      - username must be unique
 *      - email must be unique
 *
 * Fields:
 * - userId    : Primary key (auto-generated)
 * - username  : Unique username used for login
 * - firstName : User's first name
 * - lastName  : User's last name
 * - email     : Unique email address
 * - password  : Encrypted password (stored using PasswordEncoder)
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_users_email", columnNames = "email")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

}
