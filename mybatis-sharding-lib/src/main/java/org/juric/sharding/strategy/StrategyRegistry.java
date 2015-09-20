package org.juric.sharding.strategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyRegistry {
    private final static ConcurrentHashMap<Class<? extends ShardingStrategy>, ShardingStrategy> map = new ConcurrentHashMap<Class<? extends ShardingStrategy>, ShardingStrategy>();

    public static ShardingStrategy resolve(Class<? extends ShardingStrategy> cls) {
        if (!map.containsKey(cls)) {
            ShardingStrategy strategy;
            try {
                strategy = cls.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            map.putIfAbsent(cls, strategy);
        }

        return map.get(cls);
    }
}
