package org.juric.sharding.strategy;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalStrategy extends AbstractShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Method method, Object arg) {
        if (!(arg instanceof Integer)) {
            throw new IllegalArgumentException();
        }
        return new StrategyResult(null, new int[((Integer) arg).intValue()]);
    }
}
