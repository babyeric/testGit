package com.practice;

import com.practice.config.EnvironmentAwarePropertyConfigurer;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;


@EnableAutoConfiguration
@ComponentScan
public class Application {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    static EnvironmentAwarePropertyConfigurer propertyPlaceHolderConfigurer() {
        EnvironmentAwarePropertyConfigurer pagePropertySourcePlaceHolderConfigurer = new EnvironmentAwarePropertyConfigurer();
        pagePropertySourcePlaceHolderConfigurer.setPropertyBags(CollectionUtils.arrayToList(new String[]{"db"}));
        return pagePropertySourcePlaceHolderConfigurer;
    }
}
