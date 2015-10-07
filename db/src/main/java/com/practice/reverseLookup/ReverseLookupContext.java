package com.practice.reverseLookup;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReverseLookupContext {
    String value();
}
