package com.practice.abc.lazyPop.annotation;

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
}
