package com.icu.mybill.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新排序DTO")
public class UpdateSortDTO {
    @NotNull(message = "排序id不能为空")
    @Schema(description = "排序id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @NotNull(message = "排序字段不能为空")
    @Schema(description = "排序字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;
}

