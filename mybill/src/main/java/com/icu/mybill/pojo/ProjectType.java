package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 项目类型表
 * @TableName project_type
 */
@TableName(value ="project_type")
@Data
public class ProjectType {
    /**
     * 项目类型id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目类型名称
     */
    private String name;

    /**
     * 项目类型图标
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