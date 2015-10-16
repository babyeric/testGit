package com.juric.carbon.schema.user;

import com.juric.carbon.schema.base.BaseSchema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Eric on 10/3/2015.
 */
public class UserPassword extends BaseSchema {
    @NotNull
    private Long userId;
    @NotNull
    @Size(min=8, max=30)
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

    @Override
    public String toString() {
        return "UserPassword{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                '}';
    }
}
