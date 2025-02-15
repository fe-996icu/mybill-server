package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 成员类型表
 * @TableName member_type
 */
@TableName(value ="member_type")
@Data
public class MemberType {
    /**
     * 成员类型id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 成员类型名称
     */
    private String name;

    /**
     * 成员类型图标
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
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
}