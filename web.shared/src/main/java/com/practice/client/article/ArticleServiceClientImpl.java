package com.practice.client.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceClientImpl implements ArticleService {


    @Override
    public Article save(Article article) {
        RestTemplate restTemplate = new RestTemplate();
        Article ret = restTemplate.postForObject("http://localhost:8090/1/article", article, Article.class);
        return ret;
    }

    @Override
    public Article getById(Long articleId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("articleId", articleId);

        Article ret = restTemplate.getForObject("http://localhost:8090/1/article/{articleId}", Article.class, pathVaribles);
        return ret;
    }
}
