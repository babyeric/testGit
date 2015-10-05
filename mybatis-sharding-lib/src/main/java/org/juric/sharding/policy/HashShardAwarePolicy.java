package org.juric.sharding.policy;

import org.juric.sharding.strategy.IdStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/5/15
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class HashShardAwarePolicy implements ShardAwarePolicy {
    @Override
    public Integer resolveLogicalShardId(Object arg) {
        if (arg == null) {
            return null;
        }

        return arg.hashCode() % IdStrategy.LOGICAL_SHARD_COUNT;
    }
}
