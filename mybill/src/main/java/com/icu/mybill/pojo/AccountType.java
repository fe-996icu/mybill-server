package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 账户类型表
 * @TableName account_type
 */
@TableName(value ="account_type")
@Data
public class AccountType {
    /**
     * 账户类型id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账户类型名称
     */
    private String name;

    /**
     * 账户类型图标
     */
    private String icon;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 父账户类型id（逻辑外键）
     */
    private Long parentId;

    /**
     * 所属用户id（逻辑外键）
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
}