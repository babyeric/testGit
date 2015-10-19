package com.practice.http;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Eric on 10/18/2015.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    String getLogin(@RequestParam(value = "error", required = false) String error,
                    @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        String message = null;
        if (error != null) {
            message = "Incorrect user name and password";
        } else if (logout != null) {
            message = "You have successfully logged out";
        }

        if (message != null) {
            model.addAttribute("message", message);
        }

        return "login";
    }
}
