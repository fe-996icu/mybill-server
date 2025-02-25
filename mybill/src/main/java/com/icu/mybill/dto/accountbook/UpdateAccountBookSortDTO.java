package com.icu.mybill.dto.accountbook;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新账本排序DTO")
public class UpdateAccountBookSortDTO {
    @NotNull(message = "账本id不能为空")
    @Schema(description = "账本id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @NotNull(message = "账本排序字段不能为空")
    @Schema(description = "账本排序字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;
}
