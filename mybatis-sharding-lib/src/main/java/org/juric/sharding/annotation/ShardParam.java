package org.juric.sharding.annotation;

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
