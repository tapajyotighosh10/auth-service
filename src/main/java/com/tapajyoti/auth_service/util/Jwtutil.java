package com.tapajyoti.auth_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class Jwtutil {

    private final String SECRET_KEY = "dgewyijewldmmnebfjjwekdjjewhbfhjehkjjwejhdvgfewvh";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long EXPIRATION_TIME = 1000*60*60; // 1 hour in milliseconds

    public String genreateToken(String username) {
        // Logic to generate JWT token based on the username
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token valid for 1 hours
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }
}
