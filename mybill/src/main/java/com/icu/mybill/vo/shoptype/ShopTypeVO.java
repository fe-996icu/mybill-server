package com.icu.mybill.vo.shoptype;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
// 不指定name ，用类名作为schema的名称更直观
@Schema(/* name="商家类型VO", */ description = "商家类型信息")
public class ShopTypeVO {
    /**
     * 商家类型id
     */
    @Schema(description = "商家类型id")
    private Long id;

    /**
     * 商家类型名称
     */
    @Schema(description = "商家类型名称")
    private String name;

    /**
     * 商家类型图标
     */
    @Schema(description = "商家类型图标")
    private String icon;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private Integer sort;

}