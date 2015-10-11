package com.juric;

import com.juric.carbon.schema.user.User;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Eric on 10/11/2015.
 */
public class UserPasswordMapperTests extends AbstractMapperTest {

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Test
    public void testSavePassword() {
        long userId = 1234567L;
        UserPasswordDB userPasswordDB = userPasswordDB(userId);
        int ret = userPasswordMapper.save(userPasswordDB);
        Assert.assertEquals(1, ret);

        UserPasswordDB result = userPasswordMapper.getByUserId(userId);
        Assert.assertEquals(userPasswordDB.toString(), result.toString());

        userPasswordDB.setSalt("new salt");
        userPasswordDB.setPassword("7654321");
        userPasswordDB.setVersion(2);
        userPasswordDB.setModifiedDate(new Date());
        userPasswordDB.setModifiedBy("new user");
        ret = userPasswordMapper.save(userPasswordDB);
        Assert.assertEquals(2, ret);

        result = userPasswordMapper.getByUserId(userId);
        Assert.assertEquals(userPasswordDB.toString(), result.toString());
    }

    private UserPasswordDB userPasswordDB(long userId) {
        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setUserId(userId);
        userPasswordDB.setSalt("password salt");
        userPasswordDB.setPassword("1234567");
        userPasswordDB.setVersion(1);
        userPasswordDB.setCreateDate(new Date());
        userPasswordDB.setModifiedDate(new Date());
        userPasswordDB.setModifiedBy("UT");
        return  userPasswordDB;
    }
}
