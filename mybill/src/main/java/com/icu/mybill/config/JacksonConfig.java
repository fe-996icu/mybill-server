package com.icu.mybill.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonConfig {
    /**
     * 是否开启全局时间戳格式化
     */
    @Value(value = "${com.icu.mybill.global-datetime-format-timestamp-enable}")
    private Boolean globalDateTimeFormatTimestampEnable = false;

    /**
     * 全局时间格式化格式
     */
    @Value(value = "${com.icu.mybill.global-datetime-format-pattern}")
    private String globalDateTimeFormatPattern = "yyyy-MM-dd HH:mm:ss";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(globalDateTimeFormatPattern);

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        if (globalDateTimeFormatTimestampEnable) {
            // 配置 LocalDateTime 序列化器，使用自定义的 DateTimeFormatter，将 LocalDateTime 对象转为字符串
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            // 配置 LocalDateTime 反序列化器，使用自定义的 DateTimeFormatter，将字符串转为 LocalDateTime 对象
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        }else{
            // 配置 LocalDateTime 序列化器，将 LocalDateTime 对象转换为时间戳（毫秒）
            javaTimeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                @Override
                public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeNumber(value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()); // 转换为时间戳（秒）
                }
            });

            // 配置 LocalDateTime 反序列化器，将时间戳（毫秒）转换回 LocalDateTime 对象
            javaTimeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    long timestamp = p.getLongValue(); // 获取时间戳（秒）

                    return Instant.ofEpochMilli(timestamp)  // 将时间戳转换为 Instant
                            .atZone(ZoneId.systemDefault())  // 转换为默认时区的 ZonedDateTime
                            .toLocalDateTime();  // 转换为 LocalDateTime
                }
            });
        }

        objectMapper.registerModule(javaTimeModule);
        // 全局忽略未知字段，避免 Unrecognized field 错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
