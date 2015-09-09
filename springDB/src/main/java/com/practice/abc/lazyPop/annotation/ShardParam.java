package com.practice.abc.lazyPop.annotation;

import org.apache.ibatis.annotations.Param;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardParam{
    String value();
}
