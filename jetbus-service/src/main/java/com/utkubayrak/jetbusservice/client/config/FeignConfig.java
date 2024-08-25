package com.utkubayrak.jetbusservice.client.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // Bu ayar, Feign Client çağrılarının detaylı loglanmasını sağlar
    }
}
