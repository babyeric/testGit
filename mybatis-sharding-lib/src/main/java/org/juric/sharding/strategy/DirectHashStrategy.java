package org.juric.sharding.strategy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 11:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class DirectHashStrategy extends AbstractShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Method method, Object arg) {
        arg = StrategyUtils.resolveComplexType(arg, new Functor<Object, Boolean>() {
            @Override
            public Boolean invoke(Object o) {
                if (o == null) {
                    return Boolean.FALSE;
                }

                if (o instanceof String) {
                    return Boolean.TRUE;
                }

                return Boolean.FALSE;
            }
        });

        if (arg != null) {
            int logicalShardId;
            if (arg instanceof String) {
                logicalShardId = arg.hashCode() % IdStrategy.LOGICAL_SHARD_COUNT;
            } else {
                throw new IllegalArgumentException("type not acceptable");
            }
            return new StrategyResult(new int[]{logicalShardId}, null);
        }

        //trigger shard aware id generation
        Random rn = new Random();
        return new StrategyResult(new int[]{rn.nextInt(LOGICAL_SHARD_COUNT)}, null);
    }
}
