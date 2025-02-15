package com.icu.mybill.enums;

public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOT_LOGIN(504,"notLogin"),
    USERNAME_USED(505,"userNameUsed"),
    LOGIN_FAIL(400,"账号或密码错误"),
    NOT_FOUND(404,"notFound"),
    SERVER_ERROR(500,"serverError"),
    NOT_AUTHORIZED(403,"访问受限"),
    NOT_PERMISSION(401,"无权限访问"),
    ;

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
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
