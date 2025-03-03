package com.icu.mybill.dto.attachment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="附件DTO", */ description = "附件的BaseDTO")
public class AttachmentBaseDTO {
    /**
     * 附件id
     */
    @NotBlank(message = "附件id不能为空")
    @Schema(description = "附件id")
    private String fileId;

    /**
     * 附件名称
     */
    @NotBlank(message = "附件名称不能为空")
    @Schema(description = "附件名称")
    private String fileName;

    /**
     * 附件路径
     */
    @NotBlank(message = "附件路径不能为空")
    @Schema(description = "附件路径")
    private String filePath;

    @Schema(description = "附件排序字段")
    private Integer sort;
}