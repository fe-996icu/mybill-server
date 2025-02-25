package com.icu.mybill.dto.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "更新账户类型排序DTO")
public class UpdateAccountTypeSortDTO {
    @NotNull(message = "账户类型id不能为空")
    @Schema(description = "账户类型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @NotNull(message = "账户类型排序字段不能为空")
    @Schema(description = "账户类型排序字段", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sort;
}
