package com.juric.carbon.rest.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.article.Article;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Version("1")
@RestController
public class ArticleController {
    @Resource(name = "articleService")
    private ArticleService articleService;

    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public Article createArticle(@Valid @RequestBody Article article) {
        Article ret = articleService.create(article);
        return ret;
    }

    @RequestMapping(value = "/articles", method = RequestMethod.PUT)
    public void updateArticle(@RequestBody Article article) {
        articleService.update(article);
        articleService.getById(article.getArticleId());
    }

    @RequestMapping(value = "/articles/{articleId}", method = RequestMethod.GET)
    public Article getArticleById(@PathVariable("articleId") long articleId) {
        Article ret = articleService.getById(articleId);
        return ret;
    }

    @RequestMapping(value = "/sites/{siteId}/articles", method = RequestMethod.GET)
    public List<Article> getArticlesBySite(@PathVariable long siteId, @RequestHeader(required = false) Date lastDate, @RequestHeader(required = false) Long lastId, @RequestHeader Integer pageSize) {
        return articleService.getArticlesBySite(siteId, lastDate, lastId, pageSize);
    }
}
