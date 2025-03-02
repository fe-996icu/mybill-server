package com.icu.mybill.dto.template;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "账单模版的BaseDTO")
public class TemplateBaseDTO {
    @NotBlank(message = "账单模版名称不能为空")
    @Size(max = 25, min = 1, message = "名称长度在1-25之间")
    // 设置 @NotBlank 会覆盖 @Schema 中的 requiredMode 属性为 Schema.RequiredMode.REQUIRED
    // @Size 会覆盖@Schema 中的 min 和 max 属性
    @Schema(description = "账单模版名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型，1为支出，2为收入", requiredMode = Schema.RequiredMode.REQUIRED)
    private BillType type;

    @NotNull(message = "账单分类id不能为空")
    @Schema(description = "账单分类id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long billCategoryId;

    @NotNull(message = "账户类型id不能为空")
    @Schema(description = "账户类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountTypeId;

    @NotNull(message = "账单金额不能为空")
    @Schema(description = "账单金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Schema(description = "成员类型id")
    private Long memberTypeId;

    @Schema(description = "商家类型id")
    private Long shopTypeId;

    @Schema(description = "项目类型id")
    private Long projectTypeId;

    @Schema(description = "账单备注")
    private String notes;

}