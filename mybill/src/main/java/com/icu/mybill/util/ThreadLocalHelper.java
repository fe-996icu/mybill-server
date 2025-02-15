package com.icu.mybill.util;

import com.icu.mybill.dto.TokenUserDTO;

/**
 * ThreadLocal 局部变量
 */
public class ThreadLocalHelper {
    private static final ThreadLocal<TokenUserDTO> CURRENT_LOCAL = new ThreadLocal<>();

    public static void set(TokenUserDTO value) {
        CURRENT_LOCAL.set(value);
    }

    public static TokenUserDTO get() {
        return CURRENT_LOCAL.get();
    }

    public static void remove() {
        CURRENT_LOCAL.remove();
    }
}
