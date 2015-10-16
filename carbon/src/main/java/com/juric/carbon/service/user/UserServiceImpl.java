package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;

import java.util.Date;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserPasswordService userPasswordService;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUserPasswordService(UserPasswordService userPasswordService) {
        this.userPasswordService = userPasswordService;
    }

    @Override
    public User createUser(UserCreate userCreate) {
        User user = userCreate.getUser();
        Date now = new Date();
        user.setCreateDate(now);
        user.setModifiedDate(now);

        int ret = userMapper.insert(new UserDB(userCreate.getUser()));
        if (ret  != 1) {
            throw new IllegalStateException("insert user failed");
        }
        UserPassword userPassword = new UserPassword();
        userPassword.setPassword(userCreate.getUserPassword());
        userPassword.setUserId(userCreate.getUser().getUserId());
        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);
        boolean succeed = userPasswordService.updatePassword(userPasswordUpdate);
        if (!succeed) {
            throw new IllegalStateException("create password failed");
        }
        return userCreate.getUser();
    }

    @Override
    public void updateUser(User user) {
        Date now = new Date();
        user.setModifiedDate(now);

        int ret = userMapper.update(new UserDB(user));
        if (ret == 0) {
            throw new ValidationException("user doesn't exist, userId="+user.getUserId());
        }
    }

    @Override
    public User getUserById(long userId) {
        UserDB userDB = userMapper.getById(userId);
        if (userDB == null) {
            return null;
        }
        return userDB.getUser();
    }

    @Override
    public User getUserByEmail(String email) {
        UserDB userDB = userMapper.getByEmail(email);
        if (userDB == null) {
            return null;
        }
        return userDB.getUser();
    }
}
