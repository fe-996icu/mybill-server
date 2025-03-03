package com.icu.mybill.validator;

import com.icu.mybill.annotation.AtLeastOneNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * 自定义校验器，用于验证至少传递一个字段
 */
public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {
    private String[] excludeFields;

    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        this.excludeFields = constraintAnnotation.excludeFields();  // 读取注解配置，获取排除字段
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // DTO 不能为 null
        }

        Class<?> clazz = value.getClass();

        while (clazz != null && clazz != Object.class) { // 递归遍历当前类及所有父类
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                // 跳过排除的字段（如 id）
                if (isExcluded(field.getName())) {
                    continue;
                }

                try {
                    Object fieldValue = field.get(value);
                    if (fieldValue instanceof String) {
                        if (StringUtils.isNotEmpty((String) fieldValue)) {
                            return true; // 只要有一个非空字符串，校验通过
                        }
                    } else if (fieldValue != null) {
                        return true; // 只要有一个非 null 值，校验通过
                    }
                } catch (IllegalAccessException e) {
                    System.out.println("反射获取字段值时出错：" + e.getMessage());
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass(); // 获取父类，继续循环
        }

        return false; // 如果所有字段都为空，则校验失败
    }

    private boolean isExcluded(String fieldName) {
        for (String excludeField : excludeFields) {
            if (excludeField.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
