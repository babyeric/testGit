package com.practice.user;

import com.juric.carbon.schema.user.UserPassword;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserPasswordDB {
    private Long userId;
    private String password;
    private String salt;

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
