package com.juric.carbon.schema.user;

import org.springframework.util.StringUtils;
import sun.security.validator.ValidatorException;

import javax.validation.Valid;

/**
 * Created by Eric on 10/11/2015.
 */
public class UserPasswordUpdate {
    @Valid
    private UserPassword password;

    @Valid
    private UserPassword currentPassword;

    public UserPassword getPassword() {
        return password;
    }

    public void setPassword(UserPassword password) {
        this.password = password;
    }

    public UserPassword getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(UserPassword currentPassword) {
        this.currentPassword = currentPassword;
    }

    public boolean validate(){
        if (password == null ||
                (currentPassword != null && !password.getUserId().equals(currentPassword.getUserId()))) {
            return false;
        }

        return true;
    }
}
