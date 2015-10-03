package com.practice.configurer;

import com.practice.storage.StorageResourceResolver;
import org.juric.storage.configurer.StorageConfiguration;
import org.juric.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.velocity.SpringResourceLoader;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/22/15
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */

@Configuration
@Import({WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter.class, VelocityAutoConfiguration.class, StorageConfiguration.class})
//@ConditionalOnProperty(name="application.cluster", havingValue="admin")
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    public final static String FILE_URL_PRIFIX = "/files/";

    private StorageService storageService;

    @Resource(name="storageService")
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @Value("${velocity.resource.cache.enabled}")
    private String velocityResourceCacheEnabled;

    @Autowired
    private VelocityProperties properties;

    @PostConstruct
    public void overrideVelocityProperties() {
        properties.getProperties().put(SpringResourceLoader.SPRING_RESOURCE_LOADER_CACHE, velocityResourceCacheEnabled);
    }

    @Bean
    public StorageResourceResolver storageResourceResolver() {
        StorageResourceResolver storageResourceResolver = new StorageResourceResolver();
        storageResourceResolver.setStorageService(storageService);
        return storageResourceResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(FILE_URL_PRIFIX + "**")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(storageResourceResolver());
    }
}
