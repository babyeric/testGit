package com.juric.carbon.api.article;

import com.juric.carbon.schema.article.Article;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleService {
    Article save(Article article);
    Article getById(long articleId);
    List<Article> getArticlesBySite(long siteId, Date lastDate, Long lastId, int pageSize);
}
