package com.ali.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyFeignConfig {

    @Bean
    @Primary
    public Feign.Builder feignBuilder() {
        return new Feign.Builder();
    }
}
