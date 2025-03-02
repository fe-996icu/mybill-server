package com.icu.mybill.vo.membertype;

import com.icu.mybill.enums.AccountBookType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="成员类型VO", */ description = "成员类型信息")
public class MemberTypeVO {
    /**
     * 成员类型id
     */
    @Schema(description = "成员类型id")
    private Long id;

    /**
     * 成员类型名称
     */
    @Schema(description = "成员类型名称")
    private String name;

    /**
     * 成员类型图标
     */
    @Schema(description = "成员类型图标")
    private String icon;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

}