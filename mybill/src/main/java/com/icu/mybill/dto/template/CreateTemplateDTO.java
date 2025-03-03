package com.icu.mybill.dto.template;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建账单模版的DTO")
public class CreateTemplateDTO extends TemplateBaseDTO {
    @NotNull(message = "账本id不能为空")
    @Schema(description = "账本id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountBookId;

    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型，1为支出，2为收入", requiredMode = Schema.RequiredMode.REQUIRED)
    private BillType type;
}