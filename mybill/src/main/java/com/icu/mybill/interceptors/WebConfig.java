package com.icu.mybill.interceptors;

import com.icu.mybill.converter.StringToBillTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    // 注册字符串转枚举类型转换器
    @Autowired
    private StringToBillTypeConverter stringToBillTypeConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns(
                        "/swagger-ui/**", // 放行 swagger-ui页面
                        "/v3/api-docs/**", // 放行 OpenAPI JSON
                        "/swagger-resources/**", // 排除 Swagger 资源文件
                        "/webjars/**", // 排除 Webjars 资源

                        "/public/**", // 放行登录接口
                        "/user/login", // 放行登录接口
                        "/user/logout", // 放行登出接口
                        "/error" // token校验前进行404检查，404跳转到此url，在 TokenInterceptor.java 中定义
                        // "/error/**"
                );
    }
}
