package com.icu.mybill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 附件表
 * @TableName attachment
 */
@TableName(value ="attachment")
@Data
public class Attachment {
    /**
     * 附件id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 所属用户id（逻辑外键）
     */
    private Long userId;

    /**
     * 账单id（逻辑外键）
     */
    private Long billId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;
}