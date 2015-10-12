package com.juric.carbon.service.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Created by Eric on 10/5/2015.
 */
public class UserPasswordServiceImpl extends UserPasswordServiceSupport implements UserPasswordService, InitializingBean {
    private UserPasswordMapper userPasswordMapper;

    public void setUserPasswordMapper(UserPasswordMapper userPasswordMapper) {
        this.userPasswordMapper = userPasswordMapper;
    }

    @Override
    public boolean updatePassword(UserPasswordUpdate userPasswordUpdate) {
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
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userPasswordMapper);
        Assert.notNull(hashVersion);
    }
}
