package com.practice.abc.lazyPop;

import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcMapperContext {
    private static ThreadLocal<AbcMapperContext> contextStore = new ThreadLocal<AbcMapperContext>();
    private DataSource datasource;


    public static AbcMapperContext newMapperContext() {
        Assert.isNull(contextStore.get());
        AbcMapperContext context = new AbcMapperContext();
        contextStore.set(context);
        return context;
    }

    public static AbcMapperContext clearMapperContext() {
        Assert.notNull(contextStore.get());
        AbcMapperContext context = contextStore.get();
        contextStore.set(null);
        return context;
    }

    public static AbcMapperContext getMapperContext() {
        return contextStore.get();
    }

    public void setDataSource(DataSource datasource) {
        this.datasource = datasource;
    }

    public DataSource getDataSource() {
        return datasource;
    }
}
