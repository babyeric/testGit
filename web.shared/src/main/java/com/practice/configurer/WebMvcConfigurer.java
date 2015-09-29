package com.practice.configurer;

import com.practice.client.storage.StorageServiceClient;
import com.practice.client.storage.StorageServiceClientImpl;
import com.practice.storage.StorageResourceResolver;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

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
    public final static String FILE_URL_PRIFIX = "/files/";


    @Bean
    public StorageServiceClient storageServiceClient() {
        return new StorageServiceClientImpl();
    }

    @Bean
    public StorageResourceResolver storageResourceResolver() {
        StorageResourceResolver storageResourceResolver = new StorageResourceResolver();
        storageResourceResolver.setStorageServiceClient(storageServiceClient());
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
