package com.icu.mybill.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

/**
 * Argon2加密工具
 */
public class Argon2Helper {
    private static final Argon2 ARGON2 = Argon2Factory.create();
    private static final int ITERATIONS = 3;
    private static final int MEMORY = 65536;
    private static final int PARALLELISM = 2;

    /**
     * 对密码进行哈希加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    public static String hashPassword(String password) {
        /*
            3	迭代次数 (t)	运行多少次哈希计算（提高抗 GPU 破解能力）。
            65536	内存使用量 (m)（KB）	这里是 65536 KB（= 64MB），提高抗 FPGA、ASIC 攻击能力。
            2	并行度 (p)	线程数（多线程计算，提高计算复杂度）。
            password.toCharArray()	输入密码	需要加密的明文密码。
         */
        return ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
    }

    /**
     * 对密码进行哈希校验
     *
     * @param hash     加密后的密码
     * @param password 密码
     * @return 校验结果
     */
    public static boolean checkPassword(String hash, String password) {
        return ARGON2.verify(hash, password.toCharArray());
    }
}
