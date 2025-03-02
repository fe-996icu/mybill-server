package com.icu.mybill.dto.membertype;

import com.icu.mybill.annotation.AtLeastOneNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@AtLeastOneNotNull
@Schema(description = "更新成员类型请求参数")
public class UpdateMemberTypeDTO {
    @Schema(description = "成员类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    // 添加 @NotNull 注解，表示该字段不能为空，数值类型的字段不能用 @NotBlank 注解
    @NotNull(message = "成员类型id不能为空")
    private Long id;

    @Schema(description = "成员类型名称")
    private String name;

    @Schema(description = "成员类型图标")
    private String icon;
}
