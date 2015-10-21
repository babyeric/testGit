package com.juric.carbon.api.article;

import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.article.ArticlePagerResult;

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
    Article create(Article article);
    void update(Article article);
    Article getById(long articleId);
    ArticlePagerResult getArticlesBySite(long siteId, Date lastDate, Long lastId, boolean forward, int pageSize);
}
