package com.practice.utils;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapperUtils {

    public static boolean keyUpdated(Object oldValue, Object newValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            return true;
        }

        return false;
    }
}
