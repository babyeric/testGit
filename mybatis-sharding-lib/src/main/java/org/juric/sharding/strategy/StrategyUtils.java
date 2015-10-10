package org.juric.sharding.strategy;

import org.juric.sharding.annotation.ShardAwareId;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyUtils {

    public static Object resolveComplexType(Object arg, ShardingStrategy.Functor<Object, Boolean> acceptor) {
        if (acceptor.invoke(arg)) {
            return arg;
        }

        if (!arg.getClass().isPrimitive()) {
            for(Method m : arg.getClass().getMethods()) {
                if (m.getReturnType().equals(Void.TYPE)) {
                    continue;
                }
                ShardAwareId shardAwareId = m.getAnnotation(ShardAwareId.class);
                if (shardAwareId != null) {
                    try {
                        Object obj = m.invoke(arg);
                        if (acceptor.invoke(obj)) {
                            return obj;
                        }
                    } catch (Exception e) {
                        throw new IllegalStateException();
                    }
                }
            }
        } else {
            return arg;
        }

        return null;
    }
}
