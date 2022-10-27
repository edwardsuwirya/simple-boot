package com.enigmacamp.hellospring.configuration;

import com.enigmacamp.hellospring.controller.interceptor.SimpleInterceptor;
import com.enigmacamp.hellospring.controller.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    SimpleInterceptor simpleInterceptor;

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(simpleInterceptor);
        registry.addInterceptor(tokenInterceptor);
    }
}
