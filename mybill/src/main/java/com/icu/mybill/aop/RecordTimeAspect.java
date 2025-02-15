package com.icu.mybill.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // 标识这是一个切面类 AOP类
@Component
public class RecordTimeAspect {
    // 这个aop方法针对 com.icu.mybill.controller 包下面所有类的所有方法（方法形参也是任意形式的）
    @Around("execution(* com.icu.mybill.controller.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取系统当前运行时间
        long startTime = System.currentTimeMillis();

        // 执行原始方式，并获取返回值
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("方法[{}] 运行时间：{}ms", joinPoint.getSignature().toString(), (endTime - startTime));
        return result;
    }

    // 使用 @annotation 切入点表达式
    // 匹配标记有 @LogAnnotation 注解的连接点
    @Around("@annotation(com.icu.mybill.annotation.LogExecutionTime)")
    public Object recordTimeByAnnotation(ProceedingJoinPoint pjp) throws Throwable {
        // 获取系统当前运行时间
        long startTime = System.currentTimeMillis();

        // 执行原始方式，并获取返回值
        Object result = pjp.proceed();

        long endTime = System.currentTimeMillis();
        log.info("方法[{}] 运行时间：{}ms [基于 @annotation 注解匹配]", pjp.getSignature().toString(), (endTime - startTime));
        return result;
    }
}
