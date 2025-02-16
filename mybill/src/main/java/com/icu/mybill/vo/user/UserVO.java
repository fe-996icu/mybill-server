package com.icu.mybill.vo.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String phone;
    private String nickname;
    private String avatar;
    private LocalDateTime regTime;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private String lastLoginDevice;
    private Integer status;
    private LocalDateTime lastUpdateTime;
}
