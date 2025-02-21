package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import com.icu.mybill.enums.AccountBookType;
import lombok.Data;

/**
 * 账本表
 * @TableName account_book
 */
@TableName(value ="account_book")
@Data
public class AccountBook {
    /**
     * 账本id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账本名称
     */
    private String name;

    /**
     * 账本图标
     */
    private String icon;

    /**
     * 账本类型：1-默认账本, 2-家庭账本, 3-生意账本
     */
    private AccountBookType type;

    /**
     * 排序字段
     */
    private Integer sort;

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