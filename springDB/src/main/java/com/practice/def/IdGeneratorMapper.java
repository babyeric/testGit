package com.practice.def;

import com.practice.abc.lazyPop.annotation.AbcDBName;
import com.practice.abc.lazyPop.annotation.ShardMethod;
import com.practice.abc.lazyPop.annotation.ShardParam;
import com.practice.abc.lazyPop.strategy.IdStrategy;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/14/15
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
@AbcDBName("test")
public interface IdGeneratorMapper {

    @ShardMethod(IdStrategy.class)
    void getSequenceNextValue(@ShardParam("idGeneratorParam") IdGeneratorParam idGeneratorParam);
}
