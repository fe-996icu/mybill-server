package com.icu.mybill.util;

import com.icu.mybill.dto.TokenUserDTO;
import com.icu.mybill.enums.LoginType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenHelper {
    @Value("${com.icu.mybill.token.secret}")
    private String SECRET;
    @Value("${com.icu.mybill.token.expire-time}")
    private Long EXPIRE_TIME;

    public String generateToken(TokenUserDTO tokenUserDTO) {
        // 1. 定义密钥
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

        // 2. 设置负载（Claims）自定义数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", tokenUserDTO.getId());
        claims.put("username", tokenUserDTO.getUsername());
        claims.put("phone", tokenUserDTO.getPhone());
        claims.put("loginType", tokenUserDTO.getLoginType().getValue());
        claims.put("lastLoginTime", tokenUserDTO.getLastLoginTime().toString());

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
        tokenUserDTO.setId(Long.parseLong(claims.get("id").toString()));
        if (claims.get("username") != null)
            tokenUserDTO.setUsername(claims.get("username").toString());
        if (claims.get("phone") != null)
            tokenUserDTO.setPhone(claims.get("phone").toString());
        tokenUserDTO.setLoginType(EnumUtils.fromValue(claims.get("loginType").toString(), LoginType.class));
        tokenUserDTO.setLastLoginTime(LocalDateTime.parse(claims.get("lastLoginTime").toString()));

        return tokenUserDTO;
    }
}
