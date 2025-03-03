package com.icu.mybill.dto.template;

import com.icu.mybill.annotation.AtLeastOneNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@AtLeastOneNotNull
@Schema(description = "更新账单模版的DTO")
public class UpdateTemplateDTO extends TemplateBaseDTO {
    @NotNull(message = "账单模版id不能为空")
    @Schema(description = "账单模版id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}