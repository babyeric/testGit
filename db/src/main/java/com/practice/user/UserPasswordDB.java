package com.practice.user;

import com.juric.carbon.schema.user.UserPassword;
import org.juric.sharding.annotation.ShardAwareId;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserPasswordDB {
    private Long userId;
    private String password;
    private String salt;

    @ShardAwareId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
