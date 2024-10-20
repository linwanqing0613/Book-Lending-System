package com.example.booklendsystem.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class TokenUtility {

    @Autowired
    private JWTProvider jwtProvider;
    public String generateToken(String phone_number) {
        return Jwts.builder()
                .setSubject(phone_number)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 小时过期
                .signWith(jwtProvider.getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // 从 token 中获取用户名
    public String extractPhoneNumber(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 从 token 中提取所有声明
    private Claims extractAllClaims(String token) {
        SecretKey key = (SecretKey) jwtProvider.getSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // 验证 token 是否有效
    public boolean validateToken(String token, String phone_number) {
        final String extractedPhoneNumber = extractPhoneNumber(token);
        return (extractedPhoneNumber.equals(phone_number) && !isTokenExpired(token));
    }

    // 检查 token 是否过期
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}