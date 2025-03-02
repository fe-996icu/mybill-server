package com.icu.mybill.converter;

import com.icu.mybill.enums.BillType;
import com.icu.mybill.util.EnumUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 字符串转枚举类型转换器
 */
@Component
public class StringToBillTypeConverter implements Converter<String, BillType> {
    @Override
    public BillType convert(@NonNull String source) {
        return EnumUtils.fromValue(source, BillType.class);
    }
}
