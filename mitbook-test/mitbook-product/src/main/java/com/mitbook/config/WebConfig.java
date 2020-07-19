package com.mitbook.config;

import com.mitbook.intercept.GlobalTransactionIdIntercept;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pengzhengfa
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalTransactionIdIntercept()).addPathPatterns("/**");
    }
    
}
