package com.icu.mybill.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.icu.mybill.util.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单查询时间范围枚举
 * */
@Getter
@AllArgsConstructor
public enum BillQueryDateRange implements EnumUtils.ValuedEnum {
    DEFAULT(1, "所有"),
    WEEK(2, "周"),
    MONTH(3, "月"),
    YEAR(4, "年");

    @JsonValue // 告诉jackson，序列化时，返回给前端的value，因为springboot用的是jackson
    @EnumValue // 告诉MybatisPlus，数据库存的是value
    private final Integer value;
    private final String desc;
}