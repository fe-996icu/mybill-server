package com.icu.mybill.enums;

/**
 * 业务状态码
 */
public enum ResultCode {

    SUCCESS(200,"success"),
    PARAMETER_FAIL(400,"请求参数错误"),
    NOT_PERMISSION(401,"无权限访问"),
    NOT_AUTHORIZED(403,"访问受限"),
    NOT_FOUND(404,"notFound"),
    METHOD_NOT_SUPPORTED(405,"请求方法不支持"),
    SERVER_ERROR(500,"对不起,服务器异常,请稍后重试"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOT_LOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),

    USERNAME_OR_PASSWORD_ERROR(1001,"用户名或密码错误"),
    USER_NOT_EXIST(1002,"用户不存在"),
    USER_NOT_AVAILABLE(1003,"用户状态不可用"),
    ;

    private Integer code;
    private String message;
    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
