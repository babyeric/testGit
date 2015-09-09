package com.practice.abc.lazyPop.strategy;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ShardingStrategy {
    StrategyResult resolve(String logcailDbName, Method method, Object[] args);
}
