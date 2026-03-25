package com.priya.user_management_system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JwtProperties
 *
 * Configuration class for managing JWT-related settings.
 * The values are loaded automatically from the application.yml or application.properties file
 * using the prefix app.jwt
 */
@Component
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JwtProperties {

    /**
     * Secret key used for signing and verifying JWT tokens.
     */
    private String secret;

    /**
     * Token expiration time in milliseconds.
     */
    private long expiration;

    /**
     * Refresh token expiration time in milliseconds.
     */
    private long refreshExpiration;
}
