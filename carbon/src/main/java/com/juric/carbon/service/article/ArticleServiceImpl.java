package com.juric.carbon.service.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticleServiceImpl extends ArticleServiceSupport implements ArticleService {
    private ArticleMapper articleMapper;

    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Article save(Article article) {
        ArticleDB articleDB = new ArticleDB(article);
        articleMapper.insert(articleDB);
        return articleDB.getArticle();
    }

    @Override
    public Article getById(long articleId) {
        ArticleDB articleDB = articleMapper.selectOne(articleId);
        return articleDB.getArticle();
    }

    @Override
    public List<Article> getArticlesBySite(long siteId, Date lastDate, Long lastId, int pageSize) {
        return null;
    }
}
