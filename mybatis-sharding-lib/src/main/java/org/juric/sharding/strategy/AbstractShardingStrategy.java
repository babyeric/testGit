package org.juric.sharding.strategy;


import org.juric.sharding.annotation.ShardParam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractShardingStrategy implements ShardingStrategy {
    @Override
    public StrategyResult resolve(String logcailDbName, Method method, Object[] args) {
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i=0; i<annotations.length; ++i) {
            for (int j=0; j<annotations[i].length; ++j) {
                if (annotations[i][j] instanceof ShardParam) {
                    return resolve(logcailDbName, args[i]);
                }
            }
        }

        throw new IllegalStateException("shardParam not found");
    }


    public abstract StrategyResult resolve(String logcailDbName, Object arg);
}
