package com.practice.client.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;

/**
 * Created by Eric on 10/18/2015.
 */
public class UserPasswordServiceClient implements UserPasswordService {
    @Override
    public boolean updatePassword(UserPasswordUpdate userPasswordUpdate) {
        return false;
    }

    @Override
    public boolean verifyPassword(UserPassword userPassword) {
        return false;
    }

    @Override
    public User authticate(String email, String password) {
        return null;
    }
}
