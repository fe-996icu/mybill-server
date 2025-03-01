package com.icu.mybill.dto.billcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "创建子账单分类的DTO")
public class CreateChildrenBillCategoryDTO extends CreateBillCategoryBaseDTO {
    @NotNull(message = "父账单分类ID不能为空")
    @Schema(description = "父账单分类ID，只允许有一级和二级，null为一级，非null为二级")
    private Long parentId;
}
