package com.icu.mybill.dto.user;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginByUsernameDTO {
    @NotBlank(message = "用户名不能为空") // `@NotBlank` 既校验 `null` 也校验 `""`
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;

    @NotBlank(message = "设备不能为空")
    @Schema(description = "登录设备", example = "Chrome")
    private String device;

    @Hidden
    private String lastLoginIp;
}
