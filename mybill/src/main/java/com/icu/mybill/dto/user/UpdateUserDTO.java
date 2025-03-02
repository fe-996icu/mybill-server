package com.icu.mybill.dto.user;

import com.icu.mybill.annotation.AtLeastOneNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@AtLeastOneNotNull
@Schema(description = "更新用户信息DTO")
public class UpdateUserDTO {
    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称", example = "zhangsan")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;
}
