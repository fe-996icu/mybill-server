package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 商家类型表
 * @TableName shop_type
 */
@TableName(value ="shop_type")
@Data
public class ShopType {
    /**
     * 商家类型id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商家类型名称
     */
    private String name;

    /**
     * 商家类型图标
     */
    private String icon;

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