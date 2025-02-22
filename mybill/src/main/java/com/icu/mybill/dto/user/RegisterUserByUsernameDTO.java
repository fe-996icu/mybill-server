package com.icu.mybill.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Schema(description = "通过账号注册用户DTO")
public class RegisterUserByUsernameDTO {
    @NotBlank(message = "用户名不能为空")
    // 校验用户名长度和必须满足正则表达式
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,25}$", message = "用户名长度必须在6到25位之间，且只能包含字母、数字、下划线")
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    @NotBlank(message = "密码不能为空")
    // 校验密码长度和必须满足正则表达式
    @Pattern(regexp = "^[a-zA-Z0-9_]{6,25}$", message = "密码长度必须在6到25位之间，且只能包含字母、数字、下划线")
    @Schema(description = "密码", example = "123456")
    private String password;
}
