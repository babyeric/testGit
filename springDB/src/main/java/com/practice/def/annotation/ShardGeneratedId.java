package com.practice.def.annotation;

import com.practice.def.ShardGeneratedIdGroup;

import java.lang.annotation.*;

/**
 * Created by Eric on 9/13/2015.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ShardGeneratedId {
    ShardGeneratedIdGroup value();
}
