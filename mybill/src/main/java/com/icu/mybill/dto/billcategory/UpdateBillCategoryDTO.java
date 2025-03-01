package com.icu.mybill.dto.billcategory;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新账单分类请求参数")
public class UpdateBillCategoryDTO {
    @Schema(description = "账单分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    // 添加 @NotNull 注解，表示该字段不能为空，数值类型的字段不能用 @NotBlank 注解
    @NotNull(message = "账单分类id不能为空")
    private Long id;

    @Schema(description = "账单分类名称")
    private String name;

    @Schema(description = "账单类型，1为支出，2为收入")
    private BillType type;

    @Schema(description = "账本图标")
    private String icon;
}
