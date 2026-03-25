package com.priya.user_management_system.exception;

import com.priya.user_management_system.dto.general_response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler
 *
 * This class provides centralized exception handling for the entire application.
 * It ensures that all exceptions thrown from controllers or services are handled
 * in a consistent and structured manner.

 * Handled Exceptions:
 * - ResourceNotFoundException        → 404 NOT_FOUND
 * - ResourceAlreadyExistsException   → 409 CONFLICT
 * - MethodArgumentNotValidException  → 400 BAD_REQUEST (validation errors)
 * - InvalidCredentialsException      → 401 UNAUTHORIZED
 * - Generic Exception                → 500 INTERNAL_SERVER_ERROR
 *
 * This class uses @RestControllerAdvice, which allows global exception handling
 * across all controllers in the application.
 *
 * Instead of returning unstructured error responses,
 * this handler converts exceptions into standardized ErrorResponseDTO objects.
 *
 * Error response includes:
 * - Timestamp
 * - HTTP Status
 * - Custom Error Message
 * - Error message
 * - Request path
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource_Not_found", ex.getMessage(), request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Resource_Already_Exists", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("[ValidationError] Validation failed for request");

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                formatValidationErrors(fieldErrors),
                request
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(
            InvalidCredentialsException ex, WebRequest request) {

        log.warn("[Auth] Invalid credentials: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "INVALID_CREDENTIALS",
                ex.getMessage(),
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(
            Exception ex, WebRequest request) {

        log.error("[GlobalException] Unexpected error occurred", ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred. Please contact support.",
                request
        );
    }

    public ResponseEntity<ErrorResponseDTO> buildErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        ErrorResponseDTO responseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .error(error)
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(status).body(responseDTO);
    }

    private String formatValidationErrors(Map<String, String> fieldErrors) {
        return fieldErrors.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
    }
}
