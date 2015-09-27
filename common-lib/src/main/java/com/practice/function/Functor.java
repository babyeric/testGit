package com.practice.function;

/**
 * Created by Eric on 9/27/2015.
 */
public interface Functor<IN, OUT> {
    OUT invoke(IN in);
}
