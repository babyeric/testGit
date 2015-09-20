package com.practice.def;

import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/14/15
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@LogicalDbName("test")
public interface IdGeneratorMapper {

    @ShardMethod(IdStrategy.class)
    void getSequenceNextValue(@ShardParam("idGeneratorParam") IdGeneratorParam idGeneratorParam);
}
