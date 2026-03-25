package com.priya.user_management_system.dto.general_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**

 * Generic response object for API responses.
 * Contains HTTP status and message to maintain
 * a consistent response structure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private HttpStatus status;

    private String message;
}
