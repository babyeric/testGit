package org.juric.sharding.annotation;

import org.juric.sharding.policy.IdShardAwarePolicy;
import org.juric.sharding.policy.ShardAwarePolicy;
import org.juric.sharding.strategy.ShardingStrategy;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:58 AM
 * To change this template use File | Settings | File Templates.
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardAwareId {
    Class<? extends ShardAwarePolicy> value() default IdShardAwarePolicy.class;
}
