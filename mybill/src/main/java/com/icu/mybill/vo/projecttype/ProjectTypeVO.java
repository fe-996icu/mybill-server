package com.icu.mybill.vo.projecttype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="项目类型VO", */ description = "项目类型信息")
public class ProjectTypeVO {
    /**
     * 项目类型id
     */
    @Schema(description = "项目类型id")
    private Long id;

    /**
     * 项目类型名称
     */
    @Schema(description = "项目类型名称")
    private String name;

    /**
     * 项目类型图标
     */
    @Schema(description = "项目类型图标")
    private String icon;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

}