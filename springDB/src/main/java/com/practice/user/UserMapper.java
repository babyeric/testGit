package com.practice.user;

import com.practice.abc.lazyPop.annotation.AbcDBName;
import com.practice.abc.lazyPop.annotation.ShardMethod;
import com.practice.abc.lazyPop.annotation.ShardParam;
import com.practice.abc.lazyPop.strategy.IdStrategy;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/12/15
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
@AbcDBName("test")
public interface UserMapper {
    @ShardMethod(IdStrategy.class)
    int insert(@ShardParam("userDB") UserDB userDB);

    @ShardMethod(IdStrategy.class)
    UserDB getById(@ShardParam("userId") Long userId);
}
