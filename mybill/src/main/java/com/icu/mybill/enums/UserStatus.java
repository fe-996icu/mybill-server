package com.icu.mybill.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 用户状态
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    NORMAL(1, "正常"),
    LOCKED(0, "禁用");

    @JsonValue // 告诉jackson，序列化时，返回给前端的value，因为springboot用的是jackson
    @EnumValue // 告诉MybatisPlus，数据库存的是value
    private final Integer value;
    private final String desc;
}
