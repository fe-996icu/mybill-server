package com.icu.mybill.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

/**
 * 业务状态码
 */
@Getter
public enum ResultCode {
    SUCCESS(200,"ok"),
    PARAMETER_FAIL(400,"请求参数错误"),
    NOT_PERMISSION(401,"无权限访问"),
    NOT_AUTHORIZED(403,"访问受限"),
    RESOURCE_NOT_FOUND(404,"资源未找到"),
    METHOD_NOT_SUPPORTED(405,"请求方法不支持"),
    SERVER_ERROR(500,"对不起,服务器异常,请稍后重试"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOT_LOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),

    USERNAME_OR_PASSWORD_ERROR(1001,"用户名或密码错误"),
    USER_NOT_EXIST(1002,"用户不存在"),
    USER_NOT_AVAILABLE(1003,"用户状态不可用"),
    USERNAME_EXIST(1004,"用户名已存在"),
    PHONE_EXIST(1005,"手机号已存在"),
    PHONE_NOT_EXIST(1006,"手机号不存在"),
    PHONE_CODE_ERROR(1007,"验证码错误"),
    ;

    @JsonValue
    private final Integer code;
    private final String message;
    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static ResultCode fromCode(Object code) {
        if (code == null) {
            return null;
        }

        try {
            int intValue = Integer.parseInt(code.toString());
            return Arrays.stream(ResultCode.values())
                    .filter(type -> type.code.equals(intValue))
                    .findFirst()
                    // .orElse(null); // 或者抛出异常
                    .orElseThrow(() -> new IllegalArgumentException("无效的值: " + intValue));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
