package com.icu.mybill.vo.billcategory;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="账单分类VO", */ description = "账单分类VO")
public class BillCategoryVO {
    /**
     * 账单分类id
     */
    @Schema(description = "账单分类id")
    private Long id;

    /**
     * 账单分类名称
     */
    @Schema(description = "账单分类名称")
    private String name;

    /**
     * 账单类型，1为支出，2为收入
     */
    @Schema(description = "账单类型，1为支出，2为收入")
    private BillType type;

    /**
     * 账单分类图标
     */
    @Schema(description = "账单分类图标")
    private String icon;

    /**
     * 账单分类父节点ID
     */
    @Schema(description = "账单分类父节点ID，只允许有一级和二级，null为一级，非null为二级")
    private Long parentId;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

    /**
     * 账单分类子节点列表
     */
    @Schema(description = "账单分类子节点，没有子节点可能为null")
    private List<BillCategoryVO> children;

}