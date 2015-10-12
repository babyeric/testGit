package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;

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
    public User createUser(User user,UserPassword userPassword) {
        userMapper.insert(new UserDB(user));
        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);
        userPasswordService.updatePassword(userPasswordUpdate);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(new UserDB(user));
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
