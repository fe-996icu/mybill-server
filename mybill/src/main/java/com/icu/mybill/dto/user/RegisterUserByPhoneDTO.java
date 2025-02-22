package com.icu.mybill.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "通过手机注册用户DTO")
public class RegisterUserByPhoneDTO {
    @NotBlank(message = "手机号不能为空")
    // 校验手机号长度和必须满足正则表达式
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    @Schema(description = "手机号", example = "13800000000")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Schema(description = "验证码", example = "6666")
    private String code;
}
