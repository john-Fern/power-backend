package com.power.backend.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final Key signingKey;
    private final long expirationMs;

    public JwtService(Environment env) {
        String secret = env.getProperty("jwt.secret"); // Changed to match application.yaml
        if (secret == null || secret.isBlank())
            secret = System.getenv("JWT_SECRET");

        String expStr = env.getProperty("jwt.expiration"); // Changed to match application.yaml
        if (expStr == null || expStr.isBlank())
            expStr = System.getenv("JWT_EXPIRATION_MS");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET is not defined");
        }

        // Default to 1 day if not set
        long exp = 86400000;
        if (expStr != null && !expStr.isBlank()) {
            try {
                exp = Long.parseLong(expStr);
            } catch (NumberFormatException e) {
                // Log warning?
            }
        }

        Key key;
        try {
            byte[] decoded = Base64.getDecoder().decode(secret);
            if (decoded.length < 32)
                throw new IllegalArgumentException("Base64 < 32 bytes");
            key = Keys.hmacShaKeyFor(decoded);
        } catch (IllegalArgumentException notBase64) {
            byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
            if (raw.length < 32) {
                throw new IllegalStateException("JWT_SECRET must be at least 32 bytes");
            }
            key = Keys.hmacShaKeyFor(raw);
        }

        this.signingKey = key;
        this.expirationMs = exp;
    }

    public String generateToken(String subject) {
        return generateToken(subject, Map.of());
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        try {
            var claims = Jwts.parserBuilder().setSigningKey(signingKey).build()
                    .parseClaimsJws(token).getBody();
            return username.equalsIgnoreCase(claims.getSubject()) && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
