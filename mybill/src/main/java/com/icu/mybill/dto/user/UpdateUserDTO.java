package com.icu.mybill.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserDTO {
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    private String avatar;
}
