package com.icu.mybill.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserByPhoneDTO {
    @NotBlank(message = "手机号不能为空")
    // 校验手机号长度和必须满足正则表达式
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
