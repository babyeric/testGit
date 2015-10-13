package com.juric.carbon.rest.user;

import com.juric.carbon.api.user.UserService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Eric on 10/12/2015.
 */
@Version("1")
@RestController
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    public @ResponseBody User createUser(@RequestBody UserCreate userCreate) {
        return userService.createUser(userCreate);
    }
}
