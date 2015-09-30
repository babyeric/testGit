package com.juric.carbon.rest.mvc;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Created by Eric on 9/29/2015.
 */
public class VersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info == null) {
            return info;
        }

        Version version = AnnotationUtils.findAnnotation(method, Version.class);
        RequestCondition<?> condition = null;
        if(version != null) {
            condition = getCustomMethodCondition(method);
        } else {
            version = AnnotationUtils.findAnnotation(handlerType, Version.class);
            if(version != null) {
                condition = getCustomTypeCondition(handlerType);
            }
        }

        if (version != null) {
            return versionMappingInfo(version, condition).combine(info);
        }

        return info;
    }

    private RequestMappingInfo versionMappingInfo(Version version, RequestCondition<?> condition) {
        return new RequestMappingInfo(
                new PatternsRequestCondition(version.value(), getUrlPathHelper(), getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions()),
                new RequestMethodsRequestCondition(),
                new ParamsRequestCondition(),
                new HeadersRequestCondition(),
                new ConsumesRequestCondition(),
                new ProducesRequestCondition(),
                condition);
    }
}
