package com.juric.portal.http;


import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import com.practice.configurer.AppConfiguration;
import com.practice.configurer.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/12/15
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Import({AppConfiguration.class, WebMvcConfigurer.class})
public class JspController {
    @Resource(name="articleService")
    private ArticleService articleService;

    @RequestMapping("/article/{articleId}")
    String article(@PathVariable("articleId") long articleId, Model model) {
        Article article = articleService.getById(articleId);
        model.addAttribute("article", article);
        model.addAttribute("message", "Hello World!");
        return "index";
    }
}
