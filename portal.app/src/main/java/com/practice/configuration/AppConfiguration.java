package com.practice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/25/15
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@EnableConfigurationProperties(VelocityProperties.class)
public class AppConfiguration {

    @Autowired
    private VelocityProperties properties;

    @Bean
    public VelocityConfigurer velocityConfigurer() {
        VelocityConfigurer configurer = new VelocityConfigurer();
        applyProperties(configurer);
        return configurer;
    }

    private void applyProperties(VelocityEngineFactory factory) {
        factory.setResourceLoaderPath(this.properties.getResourceLoaderPath());
        factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        Properties velocityProperties = new Properties();
        velocityProperties.putAll(this.properties.getProperties());
        factory.setVelocityProperties(velocityProperties);
    }

}
