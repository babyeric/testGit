package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.api.user.UserService;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private UserPasswordMapper userPasswordMapper;
    private UserPasswordHasher userPasswordHasher;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUserPasswordMapper(UserPasswordMapper userPasswordMapper) {
        this.userPasswordMapper = userPasswordMapper;
    }

    @Override
    public User createUser(User user, UserPassword userPassword) {
        userMapper.insert(new UserDB(user));
        UserPasswordDB db = userPasswordHasher.hash(userPassword);
        userPasswordMapper.savePassword(db);
        return user;
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(new UserDB(user));
    }

    @Override
    public User getUserById(long userId) {
        return userMapper.getById(userId).getUser();
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getByEmail(email).getUser();
    }
}
