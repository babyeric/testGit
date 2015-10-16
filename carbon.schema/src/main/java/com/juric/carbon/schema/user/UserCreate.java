package com.juric.carbon.schema.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Eric on 10/12/2015.
 */
public class UserCreate {
    @Valid
    private User user;
    @NotNull
    @Size(min=8, max=30)
    private String userPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
