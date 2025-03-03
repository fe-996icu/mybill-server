package com.icu.mybill.vo.attachment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="附件VO", */ description = "附件VO")
public class AttachmentVO {
    /**
     * 数据id
     */
    @Schema(description = "数据id")
    private Long id;

    /**
     * 附件id
     */
    @Schema(description = "附件id")
    private String fileId;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    private String fileName;

    /**
     * 附件路径
     */
    @Schema(description = "附件路径")
    private String filePath;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

    /**
     * 所属账单id
     */
    @Schema(description = "所属账单id")
    private Long billId;

}