package com.practice.user;

import com.practice.reverseLookup.ReverseLookupContext;
import com.practice.reverseLookup.ReverseLookupService;
import com.practice.reverseLookup.ReverseLookupStrategy;
import com.practice.reverseLookup.ReverseLookupTables;
import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.DirectHashStrategy;
import org.juric.sharding.strategy.IdStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/12/15
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
@LogicalDbName("test")
public interface UserMapper {
    @ShardMethod(IdStrategy.class)
    int insert(@ShardParam("userDB") UserDB userDB);

    @ShardMethod(IdStrategy.class)
    int update(@ShardParam("userDB") UserDB userDB);

    @ShardMethod(IdStrategy.class)
    UserDB getById(@ShardParam("userId") Long userId);

    @ShardMethod(ReverseLookupStrategy.class)
    @ReverseLookupContext(ReverseLookupTables.USER_EMAIL_TO_ID_LOOKUP)
    UserDB getByEmail(@ShardParam("email") String email);
}
