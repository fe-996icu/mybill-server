package com.icu.mybill.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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


    /** 通过数值查找枚举 */
    public static BillType fromValue(Object value) {
        if (value == null) {
            return null;
        }

        try {
            int intValue = Integer.parseInt(value.toString());
            return Arrays.stream(BillType.values())
                    .filter(type -> type.value.equals(intValue))
                    .findFirst()
                    // .orElse(null); // 或者抛出异常
                    .orElseThrow(() -> new IllegalArgumentException("无效的值: " + intValue));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
