package com.icu.mybill.dto.billcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新账单分类排序DTO")
public class UpdateBillCategorySortDTO {
    @NotNull(message = "账单分类id不能为空")
    @Schema(description = "账单分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @NotNull(message = "账单分类排序字段不能为空")
    @Schema(description = "账单分类排序字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;
}
