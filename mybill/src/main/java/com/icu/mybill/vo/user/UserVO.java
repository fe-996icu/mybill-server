package com.icu.mybill.vo.user;

import com.icu.mybill.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户信息VO")
public class UserVO {
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "昵称")
    private String nickname;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "注册时间")
    private LocalDateTime regTime;
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;
    @Schema(description = "最后登录ip")
    private String lastLoginIp;
    @Schema(description = "最后登录设备")
    private String lastLoginDevice;
    @Schema(description = "用户状态")
    private UserStatus status;
    @Schema(description = "更新时间")
    private LocalDateTime lastUpdateTime;
}
