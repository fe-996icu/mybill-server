package com.icu.mybill.converter;

import com.icu.mybill.enums.BillQueryDateRange;
import com.icu.mybill.util.EnumUtils;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 账单查询时间范围枚举转换器
 */
@Component
public class StringToBillQueryDateRangeConverter implements Converter<String, BillQueryDateRange> {
    @Override
    public BillQueryDateRange convert(@NotNull String source) {
        return EnumUtils.fromValue(source, BillQueryDateRange.class);
    }
}
