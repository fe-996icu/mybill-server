package com.icu.mybill.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    // 按照接口地址前缀分组
    // @Bean
    // public GroupedOpenApi userApi() {
    //     return GroupedOpenApi.builder()
    //             .group("公开接口")
    //             .pathsToMatch("/public/**")
    //             .build();
    // }
    //
    // @Bean
    // public GroupedOpenApi productApi() {
    //     return GroupedOpenApi.builder()
    //             .group("账本")
    //             .pathsToMatch("/accountbook/**")
    //             .build();
    // }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 开启swagger接口访问时的鉴权功能，添加全局请求头，用于 JWT 认证
                .components(new Components()
                        .addSecuritySchemes("tokenAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY) // 类型为 APIKEY
                                .in(SecurityScheme.In.HEADER) // Token 放在请求头中
                                .name("token"))) // 请求头名称为 token
                // 配置swagger基本信息
                .info(new Info()
                        .title("BiuBiu账本")
                        .version("1.0.0")
                        .description("我的记账 App"));
    }
}