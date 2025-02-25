package com.icu.mybill.exception.common;

import com.icu.mybill.enums.ResultCode;
import lombok.Getter;


/**
 * 用户返回给前端的错误提示异常
 *
 * @author zhangjianzhong
 */
@Getter
public class FrontendErrorPromptException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public FrontendErrorPromptException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
