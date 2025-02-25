package com.icu.mybill.dto.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "创建账户类型的DTO")
public class CreateAccountTypeDTO {
    @NotBlank(message = "账户类型名称不能为空")
    @Size(max = 25, min = 1, message = "名称长度在1-25之间")
    // 设置 @NotBlank 会覆盖 @Schema 中的 requiredMode 属性为 Schema.RequiredMode.REQUIRED
    // @Size 会覆盖@Schema 中的 min 和 max 属性
    @Schema(description = "账户类型名称")
    private String name;

    @Schema(description = "账户类型图标")
    private String icon;

    @Schema(description = "父账本类型ID")
    private Long parentId;

    @Schema(description = "账本排序，从1开始", minimum="1")
    private Integer sort;
}
