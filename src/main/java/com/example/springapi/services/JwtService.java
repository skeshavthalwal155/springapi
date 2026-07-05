package com.example.springapi.services;

import com.example.springapi.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(User user){
        final long tokenExpiration = 300; // 5m
        Date issuedAt = new Date();
        Date expiredAt = new Date(System.currentTimeMillis() + 1000 * tokenExpiration);
        return generateToken(user, issuedAt, expiredAt);
    }

    public String generateRefreshToken(User user){
        final long tokenExpiration = 604800; // 7d
        Date issuedAt = new Date();
        Date expiredAt = new Date(System.currentTimeMillis() + 1000 * tokenExpiration);
        return generateToken(user, issuedAt, expiredAt);
    }

    private String generateToken(User user, Date issuedAt, Date expiredAt) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(issuedAt)
                .expiration(expiredAt)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public boolean validateToken(String token){
       try{
           var claims = getClaims(token);
           return claims.getExpiration().after(new Date());
       }catch (JwtException ex){
           return false;
       }
    }

    private Claims getClaims(String token) {
       return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public Long getUserIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }
}
