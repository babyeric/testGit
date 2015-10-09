package org.juric.sharding.mapper;

import javax.sql.DataSource;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingMapperContext {
    private static ThreadLocal<Stack<ShardingMapperContext>> contextStore = new ThreadLocal<Stack<ShardingMapperContext>>();
    private DataSource dataSource;
    private String sqlSessionHolderKey;

    public static ShardingMapperContext newMapperContext() {
        Stack<ShardingMapperContext> contexts = contextStore.get();
        if (contexts == null) {
            contexts = new Stack<ShardingMapperContext>();
            contextStore.set(contexts);
        }
        ShardingMapperContext context = new ShardingMapperContext();
        contexts.push(context);
        return context;
    }

    public static ShardingMapperContext clearMapperContext() {
        Stack<ShardingMapperContext> contexts = contextStore.get();
        return contexts.pop();
    }

    public static ShardingMapperContext getMapperContext() {
        return contextStore.get().peek();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getSqlSessionHolderKey() {
        return sqlSessionHolderKey;
    }

    public void setSqlSessionHolderKey(String logicalDBName, int shardId) {
        this.sqlSessionHolderKey = logicalDBName+shardId;
    }
}
