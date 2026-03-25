package com.priya.user_management_system.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * JWTAuthEntryPoint
 *
 * Handles unauthorized access attempts in JWT-protected endpoints.
 * When a request without valid authentication tries to access a secured resource,
 * this entry point returns a JSON response with HTTP 401 status.
 */
@Component
@Slf4j
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles unauthorized access and returns a standardized JSON response.
     *
     * @param request  Incoming HTTP request
     * @param response HTTP response to send
     * @param authException Authentication exception triggered
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        // Use custom JWT error if available
        AuthenticationException jwtException =
                (AuthenticationException) request.getAttribute("JWT_AUTHENTICATION_ERROR");

        if (jwtException != null) {
            authException = jwtException;
        }

        log.warn("Unauthorized access - Path: {}, Method: {}, Message: {}",
                request.getRequestURI(), request.getMethod(), authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Build simple JSON response
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", "Authentication failed: " + authException.getMessage());
        body.put("path", request.getRequestURI());

        try {
            response.getWriter().write(objectMapper.writeValueAsString(body));
        } catch (IOException e) {
            log.error("Error writing unauthorized response: {}", e.getMessage());
        }
    }
}
