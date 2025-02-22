package com.icu.mybill.vo.accountbook;

import com.icu.mybill.enums.AccountBookType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="账本VO", */ description = "账本信息")
public class AccountBookVO {
    /**
     * 账本id
     */
    @Schema(description = "账本id")
    private Long id;

    /**
     * 账本名称
     */
    @Schema(description = "账本名称")
    private String name;

    /**
     * 账本图标
     */
    @Schema(description = "账本图标")
    private String icon;

    /**
     * 账本类型：1-默认账本, 2-家庭账本, 3-生意账本
     */
    @Schema(description = "账本类型：1-默认账本, 2-家庭账本, 3-生意账本")
    private AccountBookType type;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

}