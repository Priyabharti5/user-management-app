package com.priya.user_management_system.security;

import com.priya.user_management_system.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * JWTUtil
 * Utility class responsible for generating, validating, and parsing JWT tokens.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTUtil {

    private final JwtProperties jwtProperties;

    /**
     * Generates a JWT access token for a given username.
     *
     * @param username the username to be encoded in the token
     * @return the generated JWT token string
     * @throws IllegalArgumentException if username is null or empty
     */
    public String generateToken(String username) {
        if (Objects.isNull(username) || username.isBlank()) {
            log.error("[JWT] Token generation failed - username is null or empty");
            throw new IllegalArgumentException("Username must not be null or empty");
        }

        log.debug("[JWT] Generating access token for user: {}", username);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtProperties.getExpiration());

        String token = Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();

        log.info("[JWT] Access token generated successfully for user: {}", username);
        return token;
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token string
     * @return the username (subject) embedded in the token, or null if invalid
     */
    public String extractUsername(String token) {
        log.debug("[JWT] Extracting username from token");
        try {
            return getTokenClaims(token).getSubject();
        } catch (JwtException e) {
            log.warn("[JWT] Failed to extract username from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Validates the JWT token by verifying signature and expiration.
     *
     * @param token the JWT token to validate
     * @return true if token is valid and not expired, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = getTokenClaims(token);
            if (claims.getExpiration().before(new Date())) {
                log.warn("[JWT] Token expired for user: {}", claims.getSubject());
                return false;
            }

            log.debug("[JWT] Token validated successfully for user: {}", claims.getSubject());
            return true;

        } catch (ExpiredJwtException e) {
            log.warn("[JWT] Token expired: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("[JWT] Invalid token: {}", e.getMessage());
        } catch (Exception e) {
            log.error("[JWT] Unexpected error during token validation", e);
        }

        return false;
    }

    /**
     * Parses and returns the claims (payload) from the given token.
     *
     * @param token the JWT token string
     * @return Claims object containing token data
     * @throws JwtException if the token is invalid
     */
    private Claims getTokenClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Returns the signing key derived from the configured secret string.
     *
     * @return SecretKey instance for signing and verifying JWT tokens
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
