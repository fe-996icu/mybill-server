package com.icu.mybill.annotation;

import com.icu.mybill.validator.AtLeastOneNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * 作用于DTO类上的验证注解，验证至少传递一个字段
 * */
@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
@Target({ElementType.TYPE}) // 作用于类
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneNotNull {
    /**
     * 校验失败时的默认消息
     * */
    String message() default "至少传递一个字段";

    /**
     * 分组校验（可选）
     * */
    Class<?>[] groups() default {};

    /**
     * 负载（通常用于扩展）
     * */
    Class<? extends Payload>[] payload() default {};  //

    /**
     * 排除的字段，如 id
     * */
    String[] excludeFields() default {"id"};
}
