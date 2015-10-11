package com.juric.carbon.service.user;

import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.service.hash.PasswordHashService;
import com.practice.user.UserPasswordDB;

/**
 * Created by Eric on 10/11/2015.
 */
public abstract class UserPasswordServiceSupport {
    private PasswordHashService passwordHashService;

    public void setPasswordHashService(PasswordHashService passwordHashService) {
        this.passwordHashService = passwordHashService;
    }

    protected UserPasswordDB hash(UserPassword userPassword) {
        return new UserPasswordDB();
    }

    protected boolean matches(UserPasswordDB userPasswordDB, UserPassword userPassword) {
        if (userPasswordDB == null || userPassword == null) {
            return false;
        }


    }

    private String hash(String password, String salt, int version) {

    }
}
