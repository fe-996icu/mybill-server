package com.icu.mybill.exception;

import com.icu.mybill.common.Result;
import com.icu.mybill.dto.accountbook.UpdateAccountBookSortDTO;
import com.icu.mybill.enums.ResultCode;
import com.icu.mybill.exception.user.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.Null;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
     * 参数校验异常，spring无法解析转换请求参数为DTO
     * 1. 没有传递参数
     * 2. 请求体格式错误，比如接收是list但传递的map
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleNotReadableBodyException(HttpMessageNotReadableException e) {
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
     * 请求参数校验异常 RequestBody检查
     * 1. 请求参数是list，并且list中的DTO字段检查会走这里
     * @param e
     * @return
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result<?> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < e.getParameterValidationResults().size(); i++) {
            ParameterValidationResult validationResult = e.getParameterValidationResults().get(i);
            List<? extends MessageSourceResolvable> allErrors = e.getAllErrors();

            FieldError fieldError = (FieldError) allErrors.get(i);

            // 异常数据在提交list参数中的索引
            Integer index = validationResult.getContainerIndex();
            // 校验不通过的字段名
            String field = fieldError.getField();
            // 前端传递过来校验不通过的字段值
            Object rejectedValue = fieldError.getRejectedValue();
            // 校验不通过的错误信息
            String defaultMessage = fieldError.getDefaultMessage();

            errors.add(String.format("第%d条的%s", index + 1, defaultMessage));
        }

        log.warn("[HandlerMethodValidationException] {}", e.getParameterValidationResults());
        return Result.build(null, ResultCode.PARAMETER_FAIL.getCode(), String.join(";", errors));
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

    /**
     * SQL语法错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result<?> handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.warn("[BadSqlGrammarException]", e);
        return Result.build(null, ResultCode.SQL_SYNTAX_ERROR);
    }
}