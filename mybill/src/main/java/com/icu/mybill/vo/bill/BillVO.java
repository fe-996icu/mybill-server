package com.icu.mybill.vo.bill;

import com.icu.mybill.enums.BillType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(/* name="账单VO", */ description = "账单VO")
public class BillVO {
    /**
     * 账单id
     */
    @Schema(description = "账单id")
    private Long id;

    /**
     * 账本id（逻辑外键）
     */
    @Schema(description = "账本id（逻辑外键）")
    private Long accountBookId;

    /**
     * 账单类型：1-支出, 2-收入
     */
    @Schema(description = "账单类型：1-支出, 2-收入")
    private BillType type;

    /**
     * 账单分类id（逻辑外键）
     */
    @Schema(description = "账单分类id（逻辑外键）")
    private Long billCategoryId;

    /**
     * 账户类型id（逻辑外键）
     */
    @Schema(description = "账户类型id（逻辑外键）")
    private Long accountTypeId;

    /**
     * 金额
     */
    @Schema(description = "账单金额")
    private BigDecimal amount;

    /**
     * 成员类型id（逻辑外键）
     */
    @Schema(description = "成员类型id（逻辑外键）")
    private Long memberTypeId;

    /**
     * 账单日期
     */
    @Schema(description = "账单日期")
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    /**
     * 商家类型id（逻辑外键）
     */
    @Schema(description = "商家类型id（逻辑外键）")
    private Long shopTypeId;

    /**
     * 项目类型id（逻辑外键）
     */
    @Schema(description = "项目类型id（逻辑外键）")
    private Long projectTypeId;

    /**
     * 所属用户id（逻辑外键）
     */
    @Schema(description = "所属用户id（逻辑外键）")
    private Long userId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;
}