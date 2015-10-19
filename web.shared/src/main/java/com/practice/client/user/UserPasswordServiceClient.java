package com.practice.client.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.schema.user.Auth;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPassword;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import com.practice.client.common.AbstractServiceClient;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Eric on 10/18/2015.
 */
public class UserPasswordServiceClient extends AbstractServiceClient implements UserPasswordService {
    @Override
    public boolean updatePassword(UserPasswordUpdate userPasswordUpdate) {
        String url = carbonRoot + "/1/credential";
        restTemplate.put(url, userPasswordUpdate);
        return false;
    }

    @Override
    public boolean verifyPassword(UserPassword userPassword) {
        throw new NotImplementedException();
    }

    @Override
    public User authticate(Auth auth) {
        String url = carbonRoot + "/1/auth";
        return restTemplate.postForObject(url, auth, User.class);
    }
}
