package com.icu.mybill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.icu.mybill.mapper") // 扫描mapper接口
public class MybillApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybillApplication.class, args);
    }

}
