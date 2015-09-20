package com.practice.def;

import org.juric.sharding.datasource.DataSourceFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by Eric on 9/19/2015.
 */
public class DataSourceFactoryImpl implements DataSourceFactory{
    public DataSource createDataSource(String host, String schema) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://" + host + "/" + schema);
        dataSource.setPassword("#Bugsfor$");
        return dataSource;
    }
}
