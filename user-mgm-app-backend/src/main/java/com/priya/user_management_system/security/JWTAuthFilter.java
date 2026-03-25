package com.priya.user_management_system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWTAuthFilter
 *
 * A Spring Security filter that validates JWT tokens for each request.
 * Extracts JWT from the Authorization header
 * Validates the token using
 * Sets authenticated user in the SecurityContext
 *
 * If the token is missing or invalid, the request continues without authentication.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Filters each request to check and validate the JWT token.
     * If valid, set authentication in the SecurityContext.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws IOException, ServletException {

        try {
            String token = extractToken(request);

            if (!StringUtils.hasText(token)) {
                log.debug("No JWT token found in Authorization header.");
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtUtil.validateToken(token)) {
                log.warn("Invalid JWT token received.");
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtil.extractUsername(token);
            if (!StringUtils.hasText(username)) {
                log.warn("JWT token missing username claim.");
                filterChain.doFilter(request, response);
                return;
            }

            // Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Create and set authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("JWT authentication successful for user: {}", username);

        } catch (Exception ex) {
            log.error("Error during JWT authentication: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from the Authorization header.
     * Expected format: "Bearer <token>"
     *
     * @param request current HTTP request
     * @return JWT token string or null if header is invalid
     */
    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
