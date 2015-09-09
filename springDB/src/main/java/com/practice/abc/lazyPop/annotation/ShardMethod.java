package com.practice.abc.lazyPop.annotation;

import com.practice.abc.lazyPop.strategy.ShardingStrategy;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardMethod {
    Class<? extends ShardingStrategy> value();
}
