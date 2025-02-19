package com.icu.mybill.common;


import com.icu.mybill.enums.ResultCode;
import lombok.ToString;

/**
 * 全局统一响应的JSON格式处理类
 *
 */
@ToString
public class Result<T> {
    // 返回码
    private Integer code;
    // 返回消息
    private String msg;
    // 返回数据
    private T data;
    public Result(){}
    // 返回数据
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMsg(message);
        return result;
    }
    public static <T> Result<T> build(T body, ResultCode resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        return result;
    }
    /**
     * 操作成功
     * @param data  baseCategory1List
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        return build(data, ResultCode.SUCCESS);
    }

    /**
     * 操作失败
     * @param resultCodeEnum
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(ResultCode resultCodeEnum){
        return build(null, resultCodeEnum);
    }

    /**
     * 操作失败
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(Integer code, String message) {
        return build(null, code, message);
    }

    public Result<T> message(String msg){
        this.setMsg(msg);
        return this;
    }
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}