package com.example.booklendsystem.config;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;

@Component
public class JWTProvider {
    @Value("${JWT_CONSTANT}")
    private String jwtSecret;

    public Key getSigningKey() {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key cannot be null or empty");
        }
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
