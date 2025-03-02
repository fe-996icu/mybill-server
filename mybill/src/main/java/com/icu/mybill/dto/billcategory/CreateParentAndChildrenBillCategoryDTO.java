package com.icu.mybill.dto.billcategory;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "创建账单分类的DTO")
public class CreateParentAndChildrenBillCategoryDTO extends CreateBillCategoryBaseDTO {

    @NotNull(message = "账单类型不能为空")
    @Schema(description = "账单类型，1为支出，2为收入")
    private BillType type;

    @NotNull(message = "子账单分类列表不能为空")
    @Size(min = 1, message = "子账单分类列表至少有一个")
    @Schema(description = "子账单分类列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private List<CreateBillCategoryBaseDTO> children;
}