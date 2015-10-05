package org.juric.sharding.policy;

import org.juric.sharding.strategy.IdStrategy;
import org.juric.sharding.strategy.StrategyResult;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/5/15
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdShardAwarePolicy implements ShardAwarePolicy {

    @Override
    public Integer resolveLogicalShardId(Object arg) {
        if (arg == null) {
            return null;
        }
        if (arg instanceof Long) {
            return (int)(((Long) arg).longValue() % IdStrategy.LOGICAL_SHARD_COUNT);
        } else if (arg instanceof Integer) {
            return ((Integer) arg).intValue() % IdStrategy.LOGICAL_SHARD_COUNT;
        } else {
            throw new IllegalStateException("type not supported");
        }
    }

}
