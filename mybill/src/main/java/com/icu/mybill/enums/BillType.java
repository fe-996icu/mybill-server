package com.icu.mybill.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单类型
 */
@Getter
@AllArgsConstructor
public enum BillType {
    EXPENSE(1, "支出"),
    INCOME(2, "收入");

    @JsonValue // 告诉jackson，序列化时，返回给前端的value，因为springboot用的是jackson
    @EnumValue // 告诉MybatisPlus，数据库存的是value
    private final Integer value;
    private final String desc;
}
