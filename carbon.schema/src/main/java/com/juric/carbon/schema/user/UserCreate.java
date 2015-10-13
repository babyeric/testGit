package com.juric.carbon.schema.user;

/**
 * Created by Eric on 10/12/2015.
 */
public class UserCreate {
    private User user;
    private UserPassword userPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(UserPassword userPassword) {
        this.userPassword = userPassword;
    }

    public boolean validate() {
        if (user == null ||
                userPassword == null ||
                !user.getUserId().equals(userPassword.getUserId())) {
            return false;
        }

        return true;
    }
}
