package com.practice.reverseLookup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReverseLookupServiceResolver {
    private static Map<Class<?>, ReverseLookupService> registry = new HashMap<>();

    public void register(Class<?> cls, ReverseLookupService service) {
        registry.put(cls, service);
    }

    public static ReverseLookupService resolve(Class<?> cls) {
        return registry.get(cls);
    }
}
