package com.icu.mybill.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "手机号验证码DTO")
public class PhoneCodeDTO {
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 4, message = "验证码长度为4")
    @Schema(description = "验证码", example = "6666")
    private String code;
}
