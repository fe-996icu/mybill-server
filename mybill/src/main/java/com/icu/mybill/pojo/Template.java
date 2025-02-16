package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.icu.mybill.enums.BillType;
import lombok.Data;

/**
 * 模板表
 * @TableName template
 */
@TableName(value ="template")
@Data
public class Template {
    /**
     * 模板id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 账单类型：1-支出, 2-收入
     */
    private BillType type;

    /**
     * 账单分类id（逻辑外键）
     */
    private Long billCategoryId;

    /**
     * 账户类型id（逻辑外键）
     */
    private Long accountTypeId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 成员类型id（逻辑外键）
     */
    private Long memberTypeId;

    /**
     * 商家类型id（逻辑外键）
     */
    private Long shopTypeId;

    /**
     * 项目类型id（逻辑外键）
     */
    private Long projectTypeId;

    /**
     * 所属用户id（逻辑外键）
     */
    private Long userId;

    /**
     * 账本id（逻辑外键）
     */
    private Long accountBookId;

    /**
     * 备注
     */
    private String notes;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}