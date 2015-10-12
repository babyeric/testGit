package com.juric.carbon.service.user;

import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.service.user.password.HashVersion;
import com.practice.user.UserPasswordDB;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Base64Utils;

import java.util.Random;

/**
 * Created by Eric on 10/11/2015.
 */
public abstract class UserPasswordServiceSupport {
    private final static int SALT_LENGTH = 64;
    protected HashVersion hashVersion;

    public void setHashVersion(HashVersion hashVersion) {
        this.hashVersion = hashVersion;
    }

    protected UserPasswordDB hash(UserPassword userPassword) {
        byte[] password = userPassword.getPassword().getBytes();
        byte[] salt = new byte[SALT_LENGTH];
        new Random().nextBytes(salt);
        byte[] bytes = ArrayUtils.addAll(password, salt);
        byte[] result = hash(bytes, null, hashVersion);
        UserPasswordDB userPasswordDB = new UserPasswordDB();
        userPasswordDB.setUserId(userPassword.getUserId());
        userPasswordDB.setCreateDate(userPassword.getCreateDate());
        userPasswordDB.setModifiedDate(userPassword.getModifiedDate());
        userPasswordDB.setModifiedBy(userPassword.getModifiedBy());
        userPasswordDB.setSalt(new String(Base64Utils.encode(salt)));
        userPasswordDB.setVersion(hashVersion.value());
        userPasswordDB.setPassword(new String(Base64Utils.encode(result)));
        return userPasswordDB;
    }

    protected boolean matches(UserPasswordDB userPasswordDB, UserPassword userPassword) {
        if (userPasswordDB == null || userPassword == null) {
            return false;
        }

        HashVersion targetVersion = HashVersion.fromValue(userPasswordDB.getVersion());
        byte[] password = userPassword.getPassword().getBytes();
        byte[] salt = Base64Utils.decode(userPasswordDB.getSalt().getBytes());
        byte[] bytes = ArrayUtils.addAll(password, salt);
        byte[] result = hash(bytes, null, targetVersion);
        byte[] expected = Base64Utils.decode(userPasswordDB.getPassword().getBytes());
        if(ArrayUtils.isEquals(expected, result)) {
            return true;
        } else {
            return false;
        }
    }

    private byte[] hash(byte[] input, HashVersion currentVersion, HashVersion targetVersion) {
        byte[] ret = input;
        HashVersion[] hashVersions = HashVersion.findRoute(currentVersion, targetVersion);
        for(HashVersion hashVersion : hashVersions) {
            ret = hashVersion.getAlgorithm().hash(ret);
        }
        return ret;
    }
}
