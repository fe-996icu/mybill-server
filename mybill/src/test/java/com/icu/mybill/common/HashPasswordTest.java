package com.icu.mybill.common;

import com.icu.mybill.util.Argon2Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class HashPasswordTest {
    @ParameterizedTest
    @ValueSource(strings = {"123456"})
    @DisplayName("测试密码加密")
    void testHashPassword(String password) {
        String hashed = Argon2Helper.hashPassword(password);
        boolean match = Argon2Helper.checkPassword(hashed, "123456");

        Assertions.assertTrue(match, "密码不匹配");
    }
}
