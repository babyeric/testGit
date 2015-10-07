package com.practice.reverseLookup;

import org.juric.sharding.strategy.ShardingStrategy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringReverseLookupService implements ReverseLookupService{
    private StringToLongLookupMapper stringToLongLookupMapper;

    public void setStringToLongLookupMapper(StringToLongLookupMapper stringToLongLookupMapper) {
        stringToLongLookupMapper = stringToLongLookupMapper;
    }

    @Override
    public int[] lookup(Object key, String tableName) {
        if (key instanceof String) {
            List<Long> results = stringToLongLookupMapper.lookup((String)key, tableName);
            int[] ret = new int[results.size()];
            for(int i=0; i<results.size(); ++i) {
                ret[i] =  (int)(results.get(i) % ShardingStrategy.LOGICAL_SHARD_COUNT);
            }
            return ret;
        } else {
            throw new IllegalArgumentException("invalid type");
        }
    }
}
