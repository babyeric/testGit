package com.practice.reverseLookup;

import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/6/15
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
@LogicalDbName("test")
public interface SiteTagToIdLookupMapper {
    @ShardMethod(IdStrategy.class)
    int insert(@ShardParam("siteTag") String siteTag, @Param("siteId") Long siteId);

    @ShardMethod(IdStrategy.class)
    int delete(@ShardParam("siteTag") String siteTag, @Param("siteId") Long siteId);

    @ShardMethod(IdStrategy.class)
    Long lookupSingle(@ShardParam("siteTag") String value);

    @ShardMethod(IdStrategy.class)
    List<Long> lookupList(@ShardParam("siteTag") String value);
}
