package com.juric.carbon.schema.user;

import com.juric.carbon.schema.base.BaseSchema;

/**
 * Created by Eric on 10/3/2015.
 */
public class UserPassword extends BaseSchema {
    private Long userId;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
