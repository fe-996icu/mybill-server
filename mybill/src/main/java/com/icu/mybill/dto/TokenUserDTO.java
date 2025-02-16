package com.icu.mybill.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储token中的用户信息
 */
@Data
public class TokenUserDTO {
    private Long id;
    private String username;
    private LocalDateTime lastLoginTime;
    private String token;
}
