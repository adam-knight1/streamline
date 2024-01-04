/*
package com.kenzie.appserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //I added this to try and debug an error I get when logging in via spring Security, these static files are expected to be in
    // /resources but are instead in /Frontend.  Still getting the bug so i may remove -Adam
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("file:Frontend/src/css/");
        registry.addResourceHandler("/pages/**").addResourceLocations("file:Frontend/src/pages/");
        registry.addResourceHandler("/api/**").addResourceLocations("file:Frontend/src/api/");
        registry.addResourceHandler("/*.html").addResourceLocations("file:Frontend/");
        registry.addResourceHandler("/pages/**").addResourceLocations("file:Frontend/src/pages/");

    }
}
*/


