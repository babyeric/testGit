package org.juric.sharding.strategy;

import org.juric.sharding.annotation.ShardAwareId;
import org.juric.sharding.policy.PolicyRegistry;
import org.juric.sharding.policy.ShardAwarePolicy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdStrategy extends AbstractShardingStrategy {
    public final static int LOGICAL_SHARD_COUNT = 100000;
    @Override
    public StrategyResult resolve(String logcailDbName, Object arg) {
        if (!arg.getClass().isPrimitive()) {
            for(Method m : arg.getClass().getMethods()) {
                if (m.getReturnType().equals(Void.TYPE)) {
                    continue;
                }
                ShardAwareId shardAwareId = m.getAnnotation(ShardAwareId.class);
                if (shardAwareId != null) {
                    try {
                        arg = m.invoke(arg);
                        if (arg != null) {
                            ShardAwarePolicy policy = PolicyRegistry.resolve(shardAwareId.value());
                            int logicalShardId = policy.resolveLogicalShardId(arg);
                            return new StrategyResult(logicalShardId, null);
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException();
                    }
                }
            }
        }

        //trigger shard aware id generation
        Random rn = new Random();
        return new StrategyResult(rn.nextInt(LOGICAL_SHARD_COUNT), null);

    }
}
