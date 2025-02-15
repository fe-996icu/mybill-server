package com.icu.mybill.exception;

/**
 * token校验失效异
 */
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message);
    }

    // 可有可无
    public TokenInvalidException() {
        super("token失效");
    }
}
