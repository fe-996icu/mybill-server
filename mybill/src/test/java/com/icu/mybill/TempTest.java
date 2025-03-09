package com.icu.mybill;

import org.junit.jupiter.api.Test;

public class TempTest {
    @Test
    public void test1() {
        String str = "    \n  \t";
        System.out.println(str.isBlank());
        System.out.println(str.isEmpty());
    }
}
