package com.practice.abc.lazyPop.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalStrategy extends AbstractShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Object arg) {
        if (!(arg instanceof Integer)) {
            throw new IllegalArgumentException();
        }
        return new StrategyResult(StrategyConstant.INVALID_ID, new int[((Integer) arg).intValue()]);
    }
}
