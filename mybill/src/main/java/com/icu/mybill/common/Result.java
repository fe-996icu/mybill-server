package com.icu.mybill.common;


import com.icu.mybill.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * 全局统一响应的JSON格式处理类
 *
 */
// 这里不能指定@Schema注解的 name 属性，否则swagger只会解析到Result.data，data下的数据不会解析生成描述
@Schema(/* name="Result", */ description = "所有接口只要进入到服务端业务，都会返回这个结果")
@ToString
@Data
public class Result<T> {
    @Schema(description = "业务状态码", example = "200")
    private ResultCode code;
    @Schema(description = "处理结果描述", example = "success")
    private String msg;
    @Schema(description = "返回数据")
    private T data;

    // 返回数据
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(ResultCode.fromCode(code));
        result.setMsg(message);
        return result;
    }
    public static <T> Result<T> build(T body, ResultCode resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum);
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
        this.setCode(ResultCode.fromCode(code));
        return this;
    }
}