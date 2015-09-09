package com.practice.abc.lazyPop.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited

public @interface AbcDBName {
    String value();
}
