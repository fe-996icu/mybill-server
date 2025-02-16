package com.icu.mybill.exception.user;

import com.icu.mybill.enums.ResultCode;
import lombok.Getter;

/**
 * 登录异常
 *
 * @author yezicom
 */
@Getter
public class LoginException extends RuntimeException {
    /**
     * 错误码
     */
    private final int code;

    public LoginException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
