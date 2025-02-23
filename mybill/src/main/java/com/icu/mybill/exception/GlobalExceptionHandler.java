package com.icu.mybill.exception;

import com.icu.mybill.common.Result;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.user.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
// @RestControllerAdvice 表示全局异常处理器，拦截所有 @Controller 抛出的异常
// @RestControllerAdvice = @ControllerAdvice + @ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("[Exception]", e);
        return Result.build(null, ResultCode.SERVER_ERROR);
    }

    /**
     * 唯一索引冲突异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.warn("[DuplicateKeyException]", e);
        String message = e.getMessage();
        int i = message.indexOf("Duplicate Entry");
        String errMsg = message.substring(i);
        String[] arr = errMsg.split(" ");
        return Result.build(null, ResultCode.SERVER_ERROR.getCode(), arr[2] + "已存在");
    }

    /**
     * 请求方法不支持异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.warn("[RequestMethodNotSupportedException]", e);
        return Result.build(null, ResultCode.METHOD_NOT_SUPPORTED);
    }

    /**
     * token校验失效异常，捕获到这类异常，提示客户端
     * @param e
     * @return
     */
    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<Result> handleTokenInvalidException(TokenInvalidException e) {
        log.warn("[TokenInvalidException]", e);
        // 构造业务结果对象
        Result result = Result.build(null, ResultCode.NOT_PERMISSION);

        // 返回 ResponseEntity，并设置 HTTP 状态码为 401
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(result);
    }


    /**
     * 参数校验异常，缺少参数，或者 参数格式错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        log.warn("[MethodArgumentNotValidException]", e);
        return Result.build(null, ResultCode.PARAMETER_FAIL.getCode(), errorMessage);
    }

    /**
     * 请求体格式错误异常
     * 参数校验异常，没有传递参数，spring无法解析转换请求参数为DTO
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleNotReadableBodyException(HttpMessageNotReadableException e) {
        String errorMessage = e.getLocalizedMessage();
        log.warn("[HttpMessageNotReadableException]", e);
        return Result.build(null, ResultCode.PARAMETER_FAIL);
    }

    /**
     * 缺少请求参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("[MissingServletRequestParameterException]", e);
        return Result.build(null, ResultCode.PARAMETER_FAIL.getCode(), String.format("缺少参数 %s %s", e.getParameterType(), e.getParameterName()));
    }
    /**
     * 登录异常
     * @param e
     * @return
     */
    @ExceptionHandler(LoginException.class)
    public Result<?> handleLoginException(LoginException e) {
        log.warn("[LoginException]", e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 资源不存在异常
     * @param e
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> handleLoginException(NoResourceFoundException e) throws NoResourceFoundException {
        log.warn("全局异 [NoResourceFoundException]", e);
        // return Result.fail(ResultCode.RESOURCE_NOT_FOUND);
        // 不做处理，直接抛出异常，让全局异常处理器处理
        throw e;
    }

}