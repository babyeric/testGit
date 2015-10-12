package com.juric.carbon.schema.user;

import org.springframework.util.StringUtils;
import sun.security.validator.ValidatorException;

/**
 * Created by Eric on 10/11/2015.
 */
public class UserPasswordUpdate {
    private UserPassword password;
    private UserPassword currentPassword;

    public UserPassword getPassword() {
        return password;
    }

    public void setPassword(UserPassword password) {
        this.password = password;
        validateUserId();
    }

    public UserPassword getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(UserPassword currentPassword) {
        this.currentPassword = currentPassword;
        validateUserId();
    }

    private void validateUserId(){
        if (password != null && currentPassword != null) {
            if (!password.getUserId().equals(currentPassword.getUserId())) {
                throw new IllegalArgumentException("userId mismatch");
            }
        }
    }
}
