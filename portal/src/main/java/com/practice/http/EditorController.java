package com.practice.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Eric on 9/24/2015.
 */
@Controller
public class EditorController {
    @RequestMapping("/editor")
    String home(Map<String, Object> model) {
        return "editor";
    }
}
