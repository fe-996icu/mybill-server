package com.icu.mybill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LoginType {
    USERNAME(1),
    PHONE(2);

    /** 登录类型 */
    private final Integer value;

    /** 通过数值查找枚举 */
    public static LoginType fromValue(Object value) {
        if (value == null) {
            return null;
        }

        try {
            int intValue = Integer.parseInt(value.toString());
            return Arrays.stream(LoginType.values())
                    .filter(type -> type.value.equals(intValue))
                    .findFirst()
                    // .orElse(null); // 或者抛出异常
                    .orElseThrow(() -> new IllegalArgumentException("无效的值: " + intValue));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
