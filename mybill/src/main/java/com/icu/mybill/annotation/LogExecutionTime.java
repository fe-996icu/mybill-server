package com.icu.mybill.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 配置元注解，表示该注解可以加在方法上
@Retention(RetentionPolicy.RUNTIME) // 配置元注解，表示该注解在运行时存在
public @interface LogExecutionTime {
}
