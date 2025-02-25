package com.icu.mybill.dto.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新账户类型请求参数")
public class UpdateAccountTypeDTO {
    @Schema(description = "账户类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    // 添加 @NotNull 注解，表示该字段不能为空，数值类型的字段不能用 @NotBlank 注解
    @NotNull(message = "账户类型id不能为空")
    private Long id;

    @Schema(description = "账户类型名称")
    private String name;

    @Schema(description = "账本图标")
    private String icon;
}
