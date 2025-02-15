package com.icu.mybill.util;

import com.icu.mybill.dto.TokenUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenHelper {
    @Value("${com.icu.token.secret}")
    private String SECRET;
    @Value("${com.icu.token.expire-time}")
    private Long EXPIRE_TIME;

    public String generateToken(TokenUserDTO tokenUserDTO) {
        // 1. 定义密钥
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        // 2. 设置负载（Claims）自定义数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", tokenUserDTO.getId());
        claims.put("username", tokenUserDTO.getUsername());

        // 3. 设置过期时间
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_TIME); // 设置过期时间

        // 4. 生成 JWT
        String token = Jwts.builder()
                .claims(claims) // 设置负载（Claims）自定义数据
                .expiration(expiration) // 设置过期时间
                .signWith(key) // 使用密钥签名
                .compact(); // 生成 JWT 字符串

        return token;
    }

    public TokenUserDTO parseToken(String token) {
        // 1. 定义密钥（必须与生成 JWT 时使用的密钥一致）
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        // 3. 解析 JWT
        Claims claims = Jwts.parser()
                .verifyWith(key) // 使用密钥验证签名
                .build()
                .parseSignedClaims(token) // 解析 JWT
                .getPayload(); // 获取 Claims

        TokenUserDTO tokenUserDTO = new TokenUserDTO();
        tokenUserDTO.setId(Integer.parseInt(claims.get("id").toString()));
        tokenUserDTO.setUsername(claims.get("username").toString());

        return tokenUserDTO;
    }
}
