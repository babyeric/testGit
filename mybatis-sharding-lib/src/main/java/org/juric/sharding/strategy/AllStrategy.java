package org.juric.sharding.strategy;

import org.juric.sharding.mapper.ShardingMapperUtils;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class AllStrategy implements ShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Method method, Object[] args) {
        int[] physicalShardIds = ShardingMapperUtils.getPhysicalShardIds(logcailDbName);
        return new StrategyResult(null, physicalShardIds);
    }
}
