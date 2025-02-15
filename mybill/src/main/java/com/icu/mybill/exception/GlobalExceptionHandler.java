package com.icu.mybill.exception;

import com.icu.mybill.common.Result;
import com.icu.mybill.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
// @RestControllerAdvice 表示全局异常处理器，拦截所有 @Controller 抛出的异常
// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("全局异常处理器，拦截到异常 [Exception]", e);
        return Result.build(null, ResultCode.SERVER_ERROR);
    }

    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("全局异常处理器，拦截到异常 [DuplicateKeyException]", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate Entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.build(null, ResultCode.SERVER_ERROR.getCode(), arr[2] + "已存在");
    }

    @ExceptionHandler
    public Result handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("全局异常处理器，拦截到异常 [RequestMethodNotSupportedException]", e);
        return Result.build(null, ResultCode.METHOD_NOT_SUPPORTED);
    }

    /**
     * token校验失效异常，捕获到这类异常，提示客户端
     * @param e
     * @return
     */
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Result> handleTokenInvalidException(TokenInvalidException e) {
        log.error("全局异常处理器，拦截到异常 [TokenInvalidException]", e);
        // 构造业务结果对象
        Result result = Result.build(null, ResultCode.NOT_PERMISSION);

        // 返回 ResponseEntity，并设置 HTTP 状态码为 401
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(result);
    }
}