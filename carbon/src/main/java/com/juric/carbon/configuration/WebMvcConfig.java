package com.juric.carbon.configuration;

import com.juric.carbon.rest.mvc.TransactionalRequestMappingHandlerAdapter;
import com.juric.carbon.rest.mvc.VersionRequestMappingHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 9/29/2015.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    private static final boolean jackson2Present =
            ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", WebMvcConfigurationSupport.class.getClassLoader()) &&
                    ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", WebMvcConfigurationSupport.class.getClassLoader());

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        VersionRequestMappingHandlerMapping handlerMapping = new VersionRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setInterceptors(getInterceptors());
        handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());

        PathMatchConfigurer configurer = getPathMatchConfigurer();
        if (configurer.isUseSuffixPatternMatch() != null) {
            handlerMapping.setUseSuffixPatternMatch(configurer.isUseSuffixPatternMatch());
        }
        if (configurer.isUseRegisteredSuffixPatternMatch() != null) {
            handlerMapping.setUseRegisteredSuffixPatternMatch(configurer.isUseRegisteredSuffixPatternMatch());
        }
        if (configurer.isUseTrailingSlashMatch() != null) {
            handlerMapping.setUseTrailingSlashMatch(configurer.isUseTrailingSlashMatch());
        }
        if (configurer.getPathMatcher() != null) {
            handlerMapping.setPathMatcher(configurer.getPathMatcher());
        }
        if (configurer.getUrlPathHelper() != null) {
            handlerMapping.setUrlPathHelper(configurer.getUrlPathHelper());
        }

        return handlerMapping;
    }

    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        addArgumentResolvers(argumentResolvers);

        List<HandlerMethodReturnValueHandler> returnValueHandlers = new ArrayList<>();
        addReturnValueHandlers(returnValueHandlers);

        TransactionalRequestMappingHandlerAdapter adapter = new TransactionalRequestMappingHandlerAdapter();
        adapter.setContentNegotiationManager(mvcContentNegotiationManager());
        adapter.setMessageConverters(getMessageConverters());
        adapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
        adapter.setCustomArgumentResolvers(argumentResolvers);
        adapter.setCustomReturnValueHandlers(returnValueHandlers);

        if (jackson2Present) {
            List<ResponseBodyAdvice<?>> interceptors = new ArrayList<>();
            interceptors.add(new JsonViewResponseBodyAdvice());
            adapter.setResponseBodyAdvice(interceptors);
        }

        return adapter;
    }
}

