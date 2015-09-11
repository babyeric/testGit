package com.practice;

import org.springframework.context.annotation.Bean;

/**
 * Created by Eric on 9/10/2015.
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean(name="migrationManager")
    MigrationManager migrationManager() {
        return new MigrationManager();
    }
}
