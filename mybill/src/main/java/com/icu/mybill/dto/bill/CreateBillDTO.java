package com.icu.mybill.dto.bill;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(/* name="创建账单DTO", */ description = "创建账单DTO")
// @JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBillDTO extends BillBaseDTO {

    @NotNull(message = "账本id不能为空")
    @Schema(description = "账本id（逻辑外键）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountBookId;

    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型：1-支出, 2-收入", requiredMode = Schema.RequiredMode.REQUIRED)
    private BillType type;
}