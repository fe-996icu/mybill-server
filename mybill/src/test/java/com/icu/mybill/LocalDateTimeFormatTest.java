package com.icu.mybill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icu.mybill.dto.bill.BillBaseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

// @SpringBootTest
public class LocalDateTimeFormatTest {
    @Test
    public void testLocalDateTimeFormat() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"name\": \"test\", \"date\": \"2025-03-05 00:00:00\"}";

        BillBaseDTO billBaseDTO = objectMapper.readValue(json, BillBaseDTO.class);
        System.out.println(billBaseDTO);  // 看看是否能正确解析

    }

    @Test
    public void testDateFormat(){
        // 创建 LocalDateTime 对象
        LocalDateTime localDateTime = LocalDateTime.parse("2024-04-03T13:50:56.789");
        // localDateTime = LocalDateTime.now();

        // 转换为带时区的 ZonedDateTime
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // 获取毫秒级别时间戳
        long timestamp = zonedDateTime.toInstant().toEpochMilli();

        System.out.println("时间戳（毫秒级别）: " + timestamp);
    }
}
