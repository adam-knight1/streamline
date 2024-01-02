package com.kenzie.appserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("file:Frontend/src/css/");
        registry.addResourceHandler("/pages/**").addResourceLocations("file:Frontend/src/pages/");
        registry.addResourceHandler("/api/**").addResourceLocations("file:Frontend/src/api/");

    }
}


