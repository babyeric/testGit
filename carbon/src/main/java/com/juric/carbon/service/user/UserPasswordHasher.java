package com.juric.carbon.service.user;

import com.juric.carbon.schema.user.UserPassword;
import com.practice.user.UserPasswordDB;

/**
 * Created by Eric on 10/5/2015.
 */
public class UserPasswordHasher {
    public UserPasswordDB hash(UserPassword userPassword) {
        return new UserPasswordDB();
    }
}
