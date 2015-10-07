package com.practice.reverseLookup;

import org.juric.sharding.strategy.AbstractShardingStrategy;
import org.juric.sharding.strategy.StrategyResult;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseLookupStrategy extends AbstractShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Method method, Object arg) {
        ReverseLookupContext reverseLookupContext = method.getAnnotation(ReverseLookupContext.class);
        String tableName = reverseLookupContext.value();

        ReverseLookupService reverseLookupService = ReverseLookupServiceResolver.resolve(arg.getClass());
        if (reverseLookupService == null) {
            throw new IllegalArgumentException("shardParam type not supported");
        }

        int[] logicalShardIds = reverseLookupService.lookup(arg, tableName);
        return new StrategyResult(logicalShardIds, null);
    }
}
