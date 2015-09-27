package com.practice.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Eric on 9/27/2015.
 */
public class ChainedMethod<T> {
    List<T> objs = new LinkedList<>();
    private Method method;
    public ChainedMethod(Method method) {
        this.method = method;
    }

    public ChainedMethod<T> add(T o) {
        objs.add(o);
        return this;
    }

    public void invoke(Object ...args) {
        for(T o: objs) {
            try {
                method.invoke(o, args);
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /*private List<Method<IN>> methods = new LinkedList<>();

    public ChainedMethod chain(Method<IN> method) {
        methods.add(method);
        return this;
    }

    @Override
    public void invoke(IN in) {
        for(Method<IN> method : methods) {
            method.invoke(in);
        }
    }*/
}
