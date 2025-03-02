package com.icu.mybill.vo.template;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Schema(/* name="模板VO", */ description = "模板VO")
@Data
public class TemplateVO {
    @Schema(description = "模板id")
    private Long id;

    @Schema(description = "模板名称")
    private String name;

    @Schema(description = "账单类型")
    private BillType type;

    @Schema(description = "账单分类id（逻辑外键）")
    private Long billCategoryId;

    @Schema(description = "账户类型id（逻辑外键）")
    private Long accountTypeId;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "成员类型id（逻辑外键）")
    private Long memberTypeId;

    @Schema(description = "商家类型id（逻辑外键）")
    private Long shopTypeId;

    @Schema(description = "项目类型id（逻辑外键）")
    private Long projectTypeId;

    @Schema(description = "用户id（逻辑外键）")
    private Long userId;

    @Schema(description = "账本id（逻辑外键）")
    private Long accountBookId;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "排序字段")
    private Integer sort;
}