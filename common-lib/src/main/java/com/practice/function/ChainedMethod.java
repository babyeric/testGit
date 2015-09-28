package com.practice.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Eric on 9/27/2015.
 */
public class ChainedMethod<T> {
    List<T> targets = new LinkedList<>();
    private Class<T> cls;
    public ChainedMethod(Class<T> cls) {
        this.cls = cls;
    }

    public ChainedMethod<T> add(T target) {
        targets.add(target);
        return this;
    }

    public void invoke(String methodName, Object ...args) {
        try {
            Method method = cls.getMethod(methodName, toClassArray(args));
            for(T target: targets) {
                method.invoke(target, args);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Class[] toClassArray(Object ...args) {
        Class[] ret = new Class[args.length];
        for (int i=0 ;i<args.length; ++i) {
            ret[i] = args[i].getClass();
        }
        return ret;
    }
}
