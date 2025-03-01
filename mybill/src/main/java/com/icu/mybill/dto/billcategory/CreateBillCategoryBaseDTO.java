package com.icu.mybill.dto.billcategory;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "创建账单分类的BaseDTO")
public class CreateBillCategoryBaseDTO {
    @NotBlank(message = "账单分类名称不能为空")
    @Size(max = 25, min = 1, message = "名称长度在1-25之间")
    // 设置 @NotBlank 会覆盖 @Schema 中的 requiredMode 属性为 Schema.RequiredMode.REQUIRED
    // @Size 会覆盖@Schema 中的 min 和 max 属性
    @Schema(description = "账单分类名称")
    private String name;

    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型，1为支出，2为收入")
    private BillType type;

    @Schema(description = "账单分类图标")
    private String icon;

    @Schema(description = "账本排序，从1开始", minimum="1")
    private Integer sort;
}