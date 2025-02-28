package com.icu.mybill.vo.accounttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

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
    @Schema(description = "账户类型父节点ID，只允许有一级和二级，null为一级，非null为二级")
    private Long parentId;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

    /**
     * 账户类型子节点列表
     */
    @Schema(description = "账户类型子节点，没有子节点可能为null")
    private List<AccountTypeVO> children;

}