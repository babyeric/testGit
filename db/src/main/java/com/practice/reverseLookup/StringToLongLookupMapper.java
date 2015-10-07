package com.practice.reverseLookup;

import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.DirectHashStrategy;
import org.juric.sharding.strategy.IdStrategy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/6/15
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@LogicalDbName("mapping")
public interface StringToLongLookupMapper {
    @ShardMethod(DirectHashStrategy.class)
    int insert(@ShardParam("key") String key, @Param("value") Long value, @Param("tableName") String tableName);

    @ShardMethod(DirectHashStrategy.class)
    int delete(@ShardParam("key") String key, @Param("value") Long siteId, @Param("tableName") String tableName);

    @ShardMethod(DirectHashStrategy.class)
    List<Long> lookup(@ShardParam("key") String key, @Param("tableName") String tableName);
}
