package com.practice.reverseLookup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReverseLookupService {
    int[] lookup(Object key, String tableName);
}
