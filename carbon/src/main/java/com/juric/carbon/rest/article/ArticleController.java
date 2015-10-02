package com.juric.carbon.rest.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.article.Article;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Version("1")
@RestController()
public class ArticleController {
    @Resource(name = "articleService")
    private ArticleService articleService;

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public Article saveArticle(@RequestBody Article article) {
        Article ret = articleService.save(article);
        return ret;
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)
    public Article getArticleById(long articleId) {
        Article ret = articleService.getById(articleId);
        return ret;
    }
}
