package com.practice.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/25/15
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@AutoConfigureAfter(VelocityAutoConfiguration.class)
@Import(VelocityAutoConfiguration.class)
public class AppConfiguration {
    @Resource
    private VelocityConfigurer velocityConfigurer;

    @PostConstruct
    public void configVelocity() {
        velocityConfigurer.getVelocityEngine().setProperty("spring.resource.loader.cache", "false");
        int i=0;
        ++i;
        return;
    }

}
