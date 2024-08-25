package com.utkubayrak.jetbusservice.config;

import com.utkubayrak.jetbusservice.component.RoleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private  RoleInterceptor roleInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Sadece belirli URL desenlerine Interceptor ekle
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/api/admin/**");
    }
}
