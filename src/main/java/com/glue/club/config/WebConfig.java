package com.glue.club.config;

import com.glue.club.common.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jiao
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public SessionInterceptor getSessionInterceptor() {
        return new SessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionInterceptor()).addPathPatterns("/**");
    }
}
