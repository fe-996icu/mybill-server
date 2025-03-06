package com.icu.mybill.dto.bill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(/* name="账单BaseDTO", */ description = "账单BaseDTO")
public class BillBaseDTO {
    @NotNull(message = "账户类型不能为空")
    @Schema(description = "账户类型id（逻辑外键）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountTypeId;

    @NotNull(message = "账单分类id不能为空")
    @Schema(description = "账单分类id（逻辑外键）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long billCategoryId;

    @NotNull(message = "账单金额不能为空")
    @Schema(description = "账单金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Schema(description = "成员类型id（逻辑外键）")
    private Long memberTypeId;

    @Schema(description = "商家类型id（逻辑外键）")
    private Long shopTypeId;

    @Schema(description = "项目类型id（逻辑外键）")
    private Long projectTypeId;

    @Schema(description = "备注")
    private String notes;

    @NotNull(message = "账单日期不能为空")
    @Schema(description = "账单日期", requiredMode = Schema.RequiredMode.REQUIRED)
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

}