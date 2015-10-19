package com.juric.carbon.rest.user;

import com.juric.carbon.api.user.UserPasswordService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserPasswordUpdate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by Eric on 10/18/2015.
 */
@Version("1")
@RestController
public class UserPasswordController {
    @Resource(name = "userPasswordService")
    private UserPasswordService userPasswordService;

    @RequestMapping(value = "/credential", method = RequestMethod.PUT)
    public void updatePassword(@Valid @RequestBody UserPasswordUpdate userPasswordUpdate) {
        userPasswordService.updatePassword(userPasswordUpdate);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.PUT)
    public @ResponseBody
    User authticate(@RequestParam String username, @RequestParam String password) {
        return userPasswordService.authticate(username, password);
    }
}
