package com.practice.configuration;

import com.practice.http.CSRFAddingHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Eric on 10/18/2015.
 */
@Configuration
public class WebMvcConfigurer2 extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new CSRFAddingHandlerInterceptor()).addPathPatterns("/**");
    }
}
