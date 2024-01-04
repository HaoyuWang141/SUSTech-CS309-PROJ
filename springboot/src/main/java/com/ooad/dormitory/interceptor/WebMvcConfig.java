package com.ooad.dormitory.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 应用 Login Interceptor

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public RateLimitInterceptor rateLimitInterceptor() {
        return new RateLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**");
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/student/notification/get2WithAuth");

        registry.addInterceptor(rateLimitInterceptor()).addPathPatterns("/**");

        // addPathPatterns("/**") 表示拦截所有请求
        // excludePathPatterns("/login", "/logout", "/css/**", "/js/**", "/images/**") 表示排除这些路径
    }
}
