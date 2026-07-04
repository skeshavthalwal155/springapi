package com.example.springapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String generateToken(String email){
        final long tokenExpiration = 86400; // 1 Day
        Date issuedAt = new Date();
        Date expiredAt = new Date(System.currentTimeMillis() + 1000 * tokenExpiration);
        return Jwts.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(expiredAt)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
