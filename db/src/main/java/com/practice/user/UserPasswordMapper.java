package com.practice.user;

import com.juric.carbon.schema.user.UserPassword;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;

/**
 * Created by Eric on 10/4/2015.
 */
@LogicalDbName("test")
public interface UserPasswordMapper {
    @ShardMethod(IdStrategy.class)
    void insert(@ShardParam("userPasswordDB") UserPasswordDB userPasswordDB);

    @ShardMethod(IdStrategy.class)
    void update(@ShardParam("userPasswordDB") UserPasswordDB userPasswordDB);

    @ShardMethod(IdStrategy.class)
    UserPasswordDB getByUserId(@ShardParam("userId") long userId);
}
