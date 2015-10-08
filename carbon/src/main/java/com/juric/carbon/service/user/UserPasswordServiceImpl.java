package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.schema.user.UserPassword;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;

/**
 * Created by Eric on 10/5/2015.
 */
public class UserPasswordServiceImpl implements UserPasswordService {
    private UserPasswordMapper userPasswordMapper;

    public void setUserPasswordMapper(UserPasswordMapper userPasswordMapper) {
        this.userPasswordMapper = userPasswordMapper;
    }

    public UserPasswordDB hash(UserPassword userPassword) {
        return new UserPasswordDB();
    }

    @Override
    public void updatePassword(UserPassword currentPassword, UserPassword newPassword) {

    }

    @Override
    public boolean verifyPassword(UserPassword userPassword) {
        return false;
    }
}
