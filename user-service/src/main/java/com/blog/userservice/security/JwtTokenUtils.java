package com.blog.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtTokenUtils {

    private final SecretKey key;

    public JwtTokenUtils(@Value("${jwt.secret}") String secret) {
        // 🔥 Giải mã Base64 giống hệt bên auth-service
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(decodedKey, "HmacSHA256");
    }public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject(); // subject = username
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject(); // email nằm trong sub
    }

    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("❌ JWT không hợp lệ: " + e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Dùng key giải mã đúng
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
