package com.juric.carbon.api.user;

import com.juric.carbon.schema.user.UserPassword;

/**
 * Created by Eric on 10/3/2015.
 */
public interface UserPasswordService {
    void updatePassword(UserPassword currentPassword, UserPassword newPassword);
    boolean verifyPassword(UserPassword userPassword);
}
