package org.juric.sharding.policy;

import org.juric.sharding.strategy.ShardingStrategy;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/5/15
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PolicyRegistry {
    private final static ConcurrentHashMap<Class<? extends ShardAwarePolicy>, ShardAwarePolicy> map = new ConcurrentHashMap<>();

    public static ShardAwarePolicy resolve(Class<? extends ShardAwarePolicy> cls) {
        if (!map.containsKey(cls)) {
            ShardAwarePolicy policy;
            try {
                policy = cls.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            map.putIfAbsent(cls, policy);
        }

        return map.get(cls);
    }
}
