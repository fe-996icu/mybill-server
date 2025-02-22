package com.icu.mybill.dto.accountbook;

import com.icu.mybill.enums.AccountBookType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "创建账本的DTO")
public class CreateAccountBookDTO {
    @NotBlank(message = "账本名称不能为空")
    @Size(max = 25, min = 1, message = "名称长度在1-25之间")
    // 设置 @NotBlank 会覆盖 @Schema 中的 requiredMode 属性为 Schema.RequiredMode.REQUIRED
    // @Size 会覆盖@Schema 中的 min 和 max 属性
    @Schema(description = "账本名称")
    private String name;

    @Schema(description = "账本图标")
    private String icon;

    @NotBlank(message = "账本类型不能为空")
    @Schema(description = "账本类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private AccountBookType type;

    // 标记参数为必填
    @Schema(description = "账本排序，从1开始", minimum="1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;
}
