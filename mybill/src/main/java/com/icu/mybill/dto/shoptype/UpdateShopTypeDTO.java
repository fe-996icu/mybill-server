package com.icu.mybill.dto.shoptype;

import com.icu.mybill.annotation.AtLeastOneNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@AtLeastOneNotNull
@Schema(description = "更新商家类型请求参数")
public class UpdateShopTypeDTO {
    @Schema(description = "商家类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    // 添加 @NotNull 注解，表示该字段不能为空，数值类型的字段不能用 @NotBlank 注解
    @NotNull(message = "商家类型id不能为空")
    private Long id;

    @Schema(description = "商家类型名称")
    private String name;

    @Schema(description = "商家类型图标")
    private String icon;
}
