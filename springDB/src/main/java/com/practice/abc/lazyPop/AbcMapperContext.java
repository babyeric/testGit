package com.practice.abc.lazyPop;

import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcMapperContext {
    private static ThreadLocal<Stack<AbcMapperContext>> contextStore = new ThreadLocal<Stack<AbcMapperContext>>();
    private DataSource dataSource;

    public static AbcMapperContext newMapperContext() {
        Stack<AbcMapperContext> contexts = contextStore.get();
        if (contexts == null) {
            contexts = new Stack<>();
            contextStore.set(contexts);
        }
        AbcMapperContext context = new AbcMapperContext();
        contexts.push(context);
        return context;
    }

    public static AbcMapperContext clearMapperContext() {
        Stack<AbcMapperContext> contexts = contextStore.get();
        return contexts.pop();
    }

    public static AbcMapperContext getMapperContext() {
        return contextStore.get().peek();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
