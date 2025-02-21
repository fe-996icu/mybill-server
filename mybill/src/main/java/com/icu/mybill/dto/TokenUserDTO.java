package com.icu.mybill.dto;

import com.icu.mybill.enums.LoginType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 存储token中的用户信息
 */
@Data
public class TokenUserDTO {
    private Long id;
    /**
     * 账号，可能为空，因为用户可能是手机号注册方式，并没有绑定账号
     */
    private String username;
    /**
     * 手机号，可能为空，因为用户可能是账号注册方式，并没有绑定手机号
     */
    private String phone;
    /**
     * 登录类型，1：账号登录，2：手机号登录
     */
    private LoginType loginType;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 登录接口才会赋值，否则为空，其他地方不需要取他
     */
    private String token;
}
