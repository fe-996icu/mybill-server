package com.icu.mybill.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) // 继承父类的equals和hashCode方法
@Data
public class BillListQuery extends BasePageQuery {
    @NotNull(message = "账本id不能为空")
    @Schema(description = "账本id（逻辑外键）")
    private Long accountBookId;
    // private Double minBalance;
    // private Double maxBalance;
    // private Integer gender;
    // private Integer status;
}
