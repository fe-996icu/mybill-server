package com.icu.mybill.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginByUsernameDTO {
    @NotBlank(message = "用户名不能为空") // `@NotBlank` 既校验 `null` 也校验 `""`
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "设备不能为空")
    private String device;

    private String lastLoginIp;
}
