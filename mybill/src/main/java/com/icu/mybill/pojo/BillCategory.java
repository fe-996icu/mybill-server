package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;

import com.icu.mybill.enums.BillType;
import lombok.Data;

/**
 * 账单分类表
 * @TableName bill_category
 */
@TableName(value ="bill_category")
@Data
public class BillCategory {
    /**
     * 账单分类id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账单分类名称
     */
    private String name;

    /**
     * 账单类型：1-支出, 2-收入
     */
    private BillType type;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 父分类id（逻辑外键）
     */
    private Long parentId;

    /**
     * 所属用户id（逻辑外键）
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}