package com.practice.http;

import com.juric.carbon.schema.article.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Eric on 10/19/2015.
 */
@Controller
public class HomePageController {
    @RequestMapping("/")
    @ResponseBody String home() {
        return "front page";
    }
}
