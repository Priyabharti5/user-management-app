package com.priya.user_management_system.dto.general_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * ErrorResponseDTO is used to return structured error responses for exceptions.
 *
 * It contains information about:
 * - timestamp : When the error occurred
 * - status    : HTTP status code
 * - error     : Custom error name
 * - message   : Detailed exception message
 * - path      : API endpoint path where the error occurred
 *
 * This class is typically used by GlobalExceptionHandler
 * to return consistent error responses across the application.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private LocalDateTime timestamp;

    private HttpStatus status;

    private String error;

    private String message;

    private String path;
}
