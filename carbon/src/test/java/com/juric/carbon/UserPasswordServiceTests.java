package com.juric.carbon;

import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.juric.carbon.service.user.password.HashVersion;
import com.juric.carbon.service.user.password.SHA512$1000;
import com.juric.carbon.service.user.UserPasswordServiceImpl;
import com.practice.user.UserPasswordDB;
import com.practice.user.UserPasswordMapper;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created by Eric on 10/11/2015.
 */
public class UserPasswordServiceTests {

    private UserPasswordServiceImpl userPasswordService;
    private UserPasswordMapper userPasswordMapper;
    @Before
    public void setUp() {
        userPasswordMapper = mock(UserPasswordMapper.class);

        userPasswordService = new UserPasswordServiceImpl();
        userPasswordService.setHashVersion(HashVersion.SHA512_1000);
        userPasswordService.setUserPasswordMapper(userPasswordMapper);
    }

    @Test
    public void testVerifyPasswordSuccess() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");

        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setSalt("salt");
        userPasswordDB.setVersion(0);
        userPasswordDB.setUserId(12345L);

        byte[] password = userPassword.getPassword().getBytes();
        byte[] salt = Base64Utils.decode(userPasswordDB.getSalt().getBytes());
        byte[] bytes = ArrayUtils.addAll(password, salt);
        byte[] result = new SHA512$1000().hash(bytes);
        userPasswordDB.setPassword(Base64Utils.encodeToString(result));

        when(userPasswordMapper.getByUserId(12345L)).thenReturn(userPasswordDB);

        boolean succeed = userPasswordService.verifyPassword(userPassword);
        Assert.assertTrue(succeed);
    }

    @Test
    public void testVerifyPasswordFail() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");

        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setSalt("salt");
        userPasswordDB.setVersion(0);
        userPasswordDB.setUserId(12345L);
        userPasswordDB.setPassword("incorrect password");

        when(userPasswordMapper.getByUserId(12345L)).thenReturn(userPasswordDB);

        boolean succeed = userPasswordService.verifyPassword(userPassword);
        Assert.assertTrue(!succeed);
    }

    @Test
    public void testCreatePassword() {
        Date date = new Date();

        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");
        userPassword.setCreateDate(date);
        userPassword.setModifiedDate(date);
        userPassword.setModifiedBy("UT");

        ArgumentCaptor<UserPasswordDB> captor = ArgumentCaptor.forClass(UserPasswordDB.class);
        when(userPasswordMapper.save(captor.capture())).thenReturn(1);

        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);

        boolean result = userPasswordService.updatePassword(userPasswordUpdate);
        Assert.assertEquals(true, result);
        UserPasswordDB userPasswordDB = captor.getValue();
        Assert.assertEquals(0, userPasswordDB.getVersion());
        Assert.assertEquals(12345L, userPasswordDB.getUserId().longValue());
        Assert.assertEquals(date, userPasswordDB.getCreateDate());
        Assert.assertEquals(date, userPasswordDB.getModifiedDate());
        Assert.assertEquals("UT", userPasswordDB.getModifiedBy());
        Assert.assertTrue(!StringUtils.isEmpty(userPasswordDB.getSalt()));
        Assert.assertTrue(!StringUtils.isEmpty(userPasswordDB.getPassword()));
    }

    @Test
    public void testCreatePasswordFailed() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");

        ArgumentCaptor<UserPasswordDB> captor = ArgumentCaptor.forClass(UserPasswordDB.class);
        when(userPasswordMapper.getByUserId(12345L)).thenReturn(new UserPasswordDB());

        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);

        boolean result = userPasswordService.updatePassword(userPasswordUpdate);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testUpdatePassword() {
        Date date = new Date();
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");
        userPassword.setCreateDate(date);
        userPassword.setModifiedDate(date);
        userPassword.setModifiedBy("UT");

        UserPassword currentPassword = new UserPassword();
        currentPassword.setUserId(12345L);
        currentPassword.setPassword("123");

        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);
        userPasswordUpdate.setCurrentPassword(currentPassword);

        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setSalt("the salt");
        userPasswordDB.setPassword("0jqTpezsF+tXGdZD0PjppPoIpAVADeySAXE1Nmc+agdyNeiZ3CHLbzgzbCmhqu396I1+sBEVOYU0b4eLU41n8g==");
        userPasswordDB.setVersion(0);
        when(userPasswordMapper.getByUserId(12345L)).thenReturn(userPasswordDB);

        ArgumentCaptor<UserPasswordDB> captor = ArgumentCaptor.forClass(UserPasswordDB.class);
        when(userPasswordMapper.save(captor.capture())).thenReturn(1);

        boolean result = userPasswordService.updatePassword(userPasswordUpdate);
        Assert.assertEquals(true, result);
        userPasswordDB = captor.getValue();
        Assert.assertEquals(0, userPasswordDB.getVersion());
        Assert.assertEquals(12345L, userPasswordDB.getUserId().longValue());
        Assert.assertEquals(date, userPasswordDB.getCreateDate());
        Assert.assertEquals(date, userPasswordDB.getModifiedDate());
        Assert.assertEquals("UT", userPasswordDB.getModifiedBy());
        Assert.assertTrue(!StringUtils.isEmpty(userPasswordDB.getSalt()));
        Assert.assertTrue(!StringUtils.isEmpty(userPasswordDB.getPassword()));
    }

    @Test
    public void testUpdatePasswordFailed() {
        Date date = new Date();
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);
        userPassword.setPassword("123");

        UserPassword currentPassword = new UserPassword();
        currentPassword.setUserId(12345L);
        currentPassword.setPassword("123");

        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);
        userPasswordUpdate.setCurrentPassword(currentPassword);

        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setSalt("the salt");
        userPasswordDB.setPassword("mock password");
        userPasswordDB.setVersion(0);
        when(userPasswordMapper.getByUserId(12345L)).thenReturn(userPasswordDB);

        boolean result = userPasswordService.updatePassword(userPasswordUpdate);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testUpdatePasswordUserMissMatch() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUserId(12345L);

        UserPassword currentPassword = new UserPassword();
        currentPassword.setUserId(54321L);

        UserPasswordUpdate userPasswordUpdate = new UserPasswordUpdate();
        userPasswordUpdate.setPassword(userPassword);
        userPasswordUpdate.setCurrentPassword(currentPassword);
        Assert.assertEquals(false, userPasswordUpdate.validate());
    }
}
