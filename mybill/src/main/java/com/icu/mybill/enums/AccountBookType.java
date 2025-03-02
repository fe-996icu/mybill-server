package com.icu.mybill.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.icu.mybill.util.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账本类型
 */
@Getter
@AllArgsConstructor
public enum AccountBookType implements EnumUtils.ValuedEnum {
    DEFAULT(1, "默认账本"),
    HOME(2, "家庭账本"),
    BUSINESS(3, "生意账本");

    @JsonValue // 告诉jackson，序列化时，返回给前端的value，因为springboot用的是jackson
    @EnumValue // 告诉MybatisPlus，数据库存的是value
    private final Integer value;
    private final String desc;
}
