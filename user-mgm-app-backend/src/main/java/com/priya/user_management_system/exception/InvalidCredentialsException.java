package com.priya.user_management_system.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * InvalidCredentialsException
 *
 * This exception is thrown when user authentication fails due to
 * invalid username or password during login.
 *
 * This is a custom runtime exception and is handled by the
 * GlobalExceptionHandler
 */
@Slf4j
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
        log.warn("[InvalidCredentialsException] {}", message);
    }
}
