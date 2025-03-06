package com.icu.mybill.dto.bill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(/* name="修改账单DTO", */ description = "修改账单DTO")
public class UpdateBillDTO extends BillBaseDTO {
    @NotNull(message = "账单id不能为空")
    @Schema(description = "账单id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}