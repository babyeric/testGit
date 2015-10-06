package com.practice.utils;

import com.practice.function.Functor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 10/5/2015.
 */
public class ListUtils {

    public static <C, V> List<V> convert(List<C> source, Functor<C, V> functor) {
        List<V> ret = new ArrayList<>();
        for(C c : source) {
            V v = functor.invoke(c);
            ret.add(v);
        }
        return ret;
    }
}
