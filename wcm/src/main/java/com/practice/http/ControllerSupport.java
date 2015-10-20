package com.practice.http;

import com.juric.carbon.schema.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Eric on 10/19/2015.
 */
public class ControllerSupport {
    User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() != null) {
            return (User)auth.getPrincipal();
        } else {
            return null;
        }
    }
}
