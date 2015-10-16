package com.juric.carbon.rest.user;

import com.juric.carbon.api.user.UserService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by Eric on 10/12/2015.
 */
@Version("1")
@RestController
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public @ResponseBody User createUser(@Valid @RequestBody UserCreate userCreate) {
        return userService.createUser(userCreate);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public void upadteUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public @ResponseBody User getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public @ResponseBody User searchUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}
