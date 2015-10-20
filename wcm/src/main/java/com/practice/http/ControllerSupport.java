package com.practice.http;

import com.juric.carbon.schema.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

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

    Long parseLongParam(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (!StringUtils.isEmpty(value)) {
            return Long.parseLong(value);
        } else {
            return null;
        }
    }
}
