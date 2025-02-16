package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

import com.icu.mybill.enums.UserStatus;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名（唯一）
     */
    private String username;

    /**
     * 用户密码（加密存储）
     */
    private String password;

    /**
     * 手机号（唯一）
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 注册时间
     */
    private LocalDateTime regTime;

    /**
     * 上次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 上次登录ip
     */
    private String lastLoginIp;

    /**
     * 上次登录设备信息
     */
    private String lastLoginDevice;

    /**
     * 账号状态：1-正常, 0-禁用
     */
    private UserStatus status;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}