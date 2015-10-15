package com.practice.client.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import com.practice.client.common.AbstractServiceClient;
import com.practice.utils.DateUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceClientImpl extends AbstractServiceClient implements ArticleService {

    @Override
    public Article save(Article article) {

        String url = carbonRoot + "/1/article";
        Article ret = restTemplate.postForObject(url, article, Article.class);
        return ret;
    }

    @Override
    public List<Article> getArticlesBySite(long siteId, Date lastDate, Long lastId, int pageSize) {

        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("siteId", siteId);

        HttpHeaders headers = new HttpHeaders();
        if (lastDate != null) {
            headers.set("lastDate", DateUtils.formatDateTime(lastDate));
        }
        if (lastId != null) {
            headers.set("lastId", lastId.toString());
        }
        headers.set("pageSize", String.valueOf(pageSize));

        HttpEntity entity = new HttpEntity(headers);
        String url = carbonRoot + "/sites/{siteId}/articles";
        ResponseEntity<List> ret = restTemplate.exchange(url, HttpMethod.GET, entity,  List.class, pathVaribles);

        return ret.getBody();
    }

    @Override
    public Article getById(long articleId) {
        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("articleId", articleId);
        String url = carbonRoot + "/1/article/{articleId}";
        Article ret = restTemplate.getForObject(url, Article.class, pathVaribles);
        return ret;
    }
}
