package com.icu.mybill.vo.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="账户类型VO", */ description = "账户类型VO")
public class AccountTypeVO {
    /**
     * 账户类型id
     */
    @Schema(description = "账户类型id")
    private Long id;

    /**
     * 账户类型名称
     */
    @Schema(description = "账户类型名称")
    private String name;

    /**
     * 账户类型图标
     */
    @Schema(description = "账户类型图标")
    private String icon;

    /**
     * 账户类型父节点ID
     */
    @Schema(description = "账户类型父节点ID")
    private Long parentId;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

}