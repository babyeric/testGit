package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserService;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordMapper;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserPasswordMapper userPasswordMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUserPasswordMapper(UserPasswordMapper userPasswordMapper) {
        this.userPasswordMapper = userPasswordMapper;
    }

    @Override
    public User createUser(User user, UserPassword userPassword) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
