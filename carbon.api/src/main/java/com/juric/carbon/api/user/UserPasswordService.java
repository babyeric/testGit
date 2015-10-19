package com.juric.carbon.api.user;

import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;

/**
 * Created by Eric on 10/3/2015.
 */
public interface UserPasswordService {
    boolean updatePassword(UserPasswordUpdate userPasswordUpdate);
    boolean verifyPassword(UserPassword userPassword);
    User authticate(String email, String password);
}
