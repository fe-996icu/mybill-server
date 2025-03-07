package com.icu.mybill;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpErrorMessageTest {
    @Test
    public void test() {
        // GET /bill/page 接口的查询参数 dateRange 参数值转换错误会抛 IllegalArgumentException 类型异常，
        // 然后被 spring 吞掉并返回 TypeMismatchException 类型异常
        // TypeMismatchException 进一步被 ModelAttributeMethodProcessor 处理，最终触发 MethodArgumentNotValidException。
        // 全局异常处理器 handleValidationException(MethodArgumentNotValidException e) 只能拿到 MethodArgumentNotValidException 异常信息
        // 所以只能拿到下面的错误信息，然后对错误信息关键词截取，取出字段名和字段值
        String str = "Failed to convert property value of type 'java.lang.String' to required type 'com.icu.mybill.enums.BillType' for property 'type'; Failed to convert from type [java.lang.String] to type [com.icu.mybill.enums.BillType] for value [3]]";

        // 正则表达式
        // /for property '(.+)'.+for value \[(.+)\].+/
        String regex = "for property '([^']+)'.+for value \\[([^\\]]+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            String propertyName = matcher.group(1);
            String propertyValue = matcher.group(2);

            System.out.println("属性名: " + propertyName);
            System.out.println("属性值: " + propertyValue);
        } else {
            System.out.println("未匹配到数据");
        }
    }
}
