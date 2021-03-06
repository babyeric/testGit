package com.practice;

import com.practice.config.EnvironmentAwarePropertyConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;

/**
 * Created by Eric on 9/10/2015.
 */

@EnableAutoConfiguration
@ComponentScan
public class FlywayApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(FlywayApplication.class, args);

        MigrationManager migrationManager = (MigrationManager)ctx.getBean("migrationManager");

        migrationManager.run(operationMode(args));
    }

    @Bean
    static EnvironmentAwarePropertyConfigurer propertyPlaceHolderConfigurer() {
        EnvironmentAwarePropertyConfigurer pagePropertySourcePlaceHolderConfigurer = new EnvironmentAwarePropertyConfigurer();
        pagePropertySourcePlaceHolderConfigurer.setPropertyBags(CollectionUtils.arrayToList(new String[]{"db"}));
        return pagePropertySourcePlaceHolderConfigurer;
    }

    private static OperationMode operationMode(String[] args) {
        return args.length == 0?
                OperationMode.MIGRATE :
                OperationMode.fromValue(args[0]);
    }

}
