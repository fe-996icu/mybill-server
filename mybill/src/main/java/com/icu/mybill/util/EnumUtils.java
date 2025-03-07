package com.icu.mybill.util;

import java.util.Arrays;

/**
 * 枚举工具类，用于将值转换为指定的枚举类型。
 */
public class EnumUtils {

    /**
     * 通用方法，用于将值转换为指定的枚举类型。
     *
     * @param value        要转换的值
     * @param enumClass    目标枚举类
     * @param <T>          枚举类型
     * @param <E>          枚举类型必须是 `Enum` 的子类
     * @return 转换后的枚举值
     * @throws IllegalArgumentException 如果值无法匹配任何枚举常量，则抛出异常
     */
    public static <T extends Enum<T> & ValuedEnum> T fromValue(Object value, Class<T> enumClass) {
        if (value == null) {
            return null;
        }

        int intValue = Integer.parseInt(value.toString());
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(type -> type.getValue().equals(intValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("无效的值: " + intValue));
    }
    
    /**
     * 用于枚举类型的接口，所有需要支持转换的枚举类型需要实现此接口。
     */
    public interface ValuedEnum {
        Integer getValue();
    }
}
