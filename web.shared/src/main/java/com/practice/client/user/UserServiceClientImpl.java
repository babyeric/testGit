package com.practice.client.user;

import com.juric.carbon.api.user.UserService;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import com.practice.client.common.AbstractServiceClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/15/15
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceClientImpl extends AbstractServiceClient implements UserService {

    @Override
    public User createUser(UserCreate userCreate) {
        String url = carbonRoot + "/1/users";
        User ret = restTemplate.postForObject(url, userCreate, User.class);
        return ret;
    }

    @Override
    public void updateUser(User user) {
        String url = carbonRoot + "/1/users";
        restTemplate.put(url, user);
    }

    @Override
    public User getUserById(long userId) {
        String url = carbonRoot + "/1/users/{userId}";
        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("userId", userId);

        return restTemplate.getForObject(url, User.class, pathVaribles);
    }

    @Override
    public User getUserByEmail(String email) {
        String url = carbonRoot + "/1/users";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email", email);
        return restTemplate.getForObject(builder.toUriString(), User.class);
    }
}
