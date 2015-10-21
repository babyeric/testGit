package com.juric.carbon.service.article;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.exception.NotFoundException;
import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.article.ArticlePagerResult;
import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;
import com.practice.function.Functor;
import com.practice.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
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
    public Article create(Article article) {
        Date now = new Date();
        article.setCreateDate(now);
        article.setModifiedDate(now);
        articleMapper.insert(new ArticleDB(article));
        return article;
    }

    @Override
    public void update(Article article) {
        article.setModifiedDate(new Date());
        articleMapper.update(new ArticleDB(article));
    }

    @Override
    public Article getById(long articleId) {
        ArticleDB articleDB = articleMapper.getById(articleId);
        return articleDB.getArticle();
    }

    @Override
    public ArticlePagerResult getArticlesBySite(long siteId, Date lastDate, Long lastId, boolean forward, int pageSize) {
        if (lastDate != null ^ lastId !=null) {
            throw new ValidationException("lastDate and lastId must be specified at the same time");
        }
        if (pageSize < 0) {
            throw new ValidationException("invalid pageSize");
        }
        boolean firstPage = lastDate == null;
        if (firstPage) {
            forward = true;
        }
        int internalPageSize = pageSize + 1;

        List<ArticleDB> articles = articleMapper.getBySite(siteId, lastDate, lastId, forward, internalPageSize);
        if (articles.isEmpty()) {
            throw new NotFoundException("can't find Articles");
        }
        boolean isFull = articles.size() == internalPageSize;
        if (isFull) {
            articles =  articles.subList(0, pageSize);
        }
        ArticlePagerResult result = new ArticlePagerResult();
        if (forward) {

            ArticleDB max = articles.get(0);
            ArticleDB min = articles.get(articles.size()-1);
            if (!firstPage) {
                result.setBackwardDate(max.getCreateDate());
                result.setBackwardId(max.getArticleId());
            }
            if (isFull) {
                result.setForwardDate(min.getCreateDate());
                result.setForwardId(min.getArticleId());
            }

        } else {
            Collections.reverse(articles);
            ArticleDB max = articles.get(0);
            ArticleDB min = articles.get(articles.size()-1);
            result.setForwardDate(min.getCreateDate());
            result.setForwardId(min.getArticleId());
            if (isFull) {
                result.setBackwardDate(max.getCreateDate());
                result.setBackwardId(max.getArticleId());
            }
        }
        result.setArticles(ListUtils.convert(articles, new Functor<ArticleDB, Article>() {
            @Override
            public Article invoke(ArticleDB articleDB) {
                return articleDB.getArticle();
            }
        }));

        return result;
    }
}
