package com.icu.mybill.dto.accountbook;

import com.icu.mybill.enums.AccountBookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAccountBookDTO {
    @NotBlank(message = "账本名称不能为空")
    @Size(max = 25, min = 1, message = "名称长度在1-25之间")

    private String name;

    private String icon;

    @NotBlank(message = "账本类型不能为空")
    private AccountBookType type;

    private Integer sort;
}
