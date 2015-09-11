package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Eric on 9/10/2015.
 */

@EnableAutoConfiguration
@ComponentScan
public class FlywayApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(FlywayApplication.class, args);

        MigrationManager migrationManager = (MigrationManager)ctx.getBean("migrationManager");
        migrationManager.migrate();
    }

}
