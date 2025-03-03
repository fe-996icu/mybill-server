package com.icu.mybill.dto.attachment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="附件DTO", */ description = "绑定附件信息到账单的DTO")
public class BindAttachmentDTO {
    @NotNull(message = "所属账单id不能为空")
    @Schema(description = "所属账单id")
    private Long billId;

    @NotNull(message = "附件列表不能为空")
    @Size(min = 1, message = "附件列表至少有1个")
    @Schema(description = "附件列表")
    @Valid // 继续校验列表中的实体数据
    private List<AttachmentBaseDTO> list;

}