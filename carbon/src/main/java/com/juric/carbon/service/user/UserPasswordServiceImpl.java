package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.exception.NotFoundException;
import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.user.Auth;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Created by Eric on 10/5/2015.
 */
public class UserPasswordServiceImpl extends UserPasswordServiceSupport implements UserPasswordService, InitializingBean {
    private UserPasswordMapper userPasswordMapper;
    protected UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void setUserPasswordMapper(UserPasswordMapper userPasswordMapper) {
        this.userPasswordMapper = userPasswordMapper;
    }

    @Override
    public boolean updatePassword(UserPasswordUpdate userPasswordUpdate) {
        if (!userPasswordUpdate.validate()) {
            throw new ValidationException("invalid userPasswordUpdate");
        }

        if (userPasswordUpdate.getCurrentPassword() !=null) {
            if (!verifyPassword(userPasswordUpdate.getCurrentPassword())) {
                return false;
            }
        } else {
            if (exists(userPasswordUpdate.getPassword().getUserId())) {
                return false;
            }
        }

        UserPasswordDB userPasswordDB = hash(userPasswordUpdate.getPassword());
        int ret = userPasswordMapper.save(userPasswordDB);
        return ret>0;
    }

    private boolean exists(long userId) {
        return userPasswordMapper.getByUserId(userId) != null;
    }

    @Override
    public boolean verifyPassword(UserPassword userPassword) {
        UserPasswordDB passwordDB = userPasswordMapper.getByUserId(userPassword.getUserId());
        if (passwordDB != null && matches(passwordDB, userPassword)) {
            return true;
        }
        return false;
    }

    @Override
    public User authticate(Auth auth) {
        UserDB userDB = userMapper.getByEmail(auth.getUsername());
        if (userDB != null) {
            UserPassword userPassword = new UserPassword();
            userPassword.setUserId(userDB.getUserId());
            userPassword.setPassword(auth.getPassword());
            if (verifyPassword(userPassword)) {
                return userDB.getUser();
            } else {
                throw new ValidationException("Invalid username or password");
            }
        } else {
            throw new NotFoundException("user not found");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userPasswordMapper);
        Assert.notNull(userMapper);
        Assert.notNull(hashVersion);
    }
}
