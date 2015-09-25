package com.practice.configurer;

import com.practice.http.StorageResourceResolver;
import org.juric.storage.service.StorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/22/15
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@Import(WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.class)
//@ConditionalOnProperty(name="application.cluster", havingValue="admin")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Resource(name = "storageService")
    private StorageService storageService;

    @Bean
    public StorageResourceResolver storageResourceResolver() {
        StorageResourceResolver storageResourceResolver = new StorageResourceResolver();
        storageResourceResolver.setStorageService(storageService);
        return storageResourceResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/files/**")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(storageResourceResolver());
    }
}
