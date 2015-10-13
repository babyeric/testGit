package com.juric.carbon.api.user;

import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import com.juric.carbon.schema.user.UserPassword;

/**
 * Created by Eric on 10/3/2015.
 */
public interface UserService {
    User createUser(UserCreate userCreate);
    void updateUser(User user);
    User getUserById(long userId);
    User getUserByEmail(String email);
}
