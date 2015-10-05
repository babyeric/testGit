package com.juric.carbon.api.user;

import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;

/**
 * Created by Eric on 10/3/2015.
 */
public interface UserService {
    User createUser(User user, UserPassword userPassword);
    void updateUser(User user);
    User getUserById(Long userId);
    User getUserByEmail(String email);
}
