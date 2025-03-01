package com.icu.mybill.dto.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "创建子账户类型的DTO")
public class CreateChildrenAccountTypeDTO extends CreateAccountTypeBaseDTO {
    @Schema(description = "父账本类型ID，只允许有一级和二级，null为一级，非null为二级")
    private Long parentId;
}
