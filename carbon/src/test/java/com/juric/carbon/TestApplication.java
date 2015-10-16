package com.juric.carbon;

import com.practice.config.EnvironmentAwarePropertyConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;

/**
 * Created by Eric on 10/9/2015.
 */
@EnableAutoConfiguration
@ComponentScan
public class TestApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    static EnvironmentAwarePropertyConfigurer propertyPlaceHolderConfigurer() {
        EnvironmentAwarePropertyConfigurer pagePropertySourcePlaceHolderConfigurer = new EnvironmentAwarePropertyConfigurer();
        pagePropertySourcePlaceHolderConfigurer.setPropertyBags(CollectionUtils.arrayToList(new String[]{"db"}));
        return pagePropertySourcePlaceHolderConfigurer;
    }
}
