package org.juric.sharding.strategy;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ShardingStrategy {
    public final static int LOGICAL_SHARD_COUNT = 100000;

    public interface Functor<IN, OUT> {
        OUT invoke(IN in);
    }

    StrategyResult resolve(String logcailDbName, Method method, Object[] args);
}
