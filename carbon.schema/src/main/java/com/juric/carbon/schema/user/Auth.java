package com.juric.carbon.schema.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Eric on 10/19/2015.
 */
public class Auth {
    @NotNull
    @Size(min=2)
    String username;
    @Size(min=8, max=30)
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
