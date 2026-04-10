package com.spring.myweb12;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowCredentials(true)   // (반드시) 쿠키 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE");

                // 이미지 파일 요청도 허용
                registry.addMapping("/file/**")
                        .allowedOrigins("http://localhost:5173/")
                        .allowCredentials(true)
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
