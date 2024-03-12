package com.heima.article.config;

import com.heima.article.interceptor.AppInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 此interceptor只负责获取userId的线程上下文信息，不校验token，所以生效路径是所有
        registry.addInterceptor(new AppInfoInterceptor())
                .addPathPatterns("/**");
    }
}