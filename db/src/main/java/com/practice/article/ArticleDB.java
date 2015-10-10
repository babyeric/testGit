package com.practice.article;

import com.juric.carbon.schema.article.Article;
import com.practice.def.ShardGeneratedIdGroup;
import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;

import java.util.Date;

/**
 * Created by Eric on 9/27/2015.
 */
public class ArticleDB {
    private Article article;

    public ArticleDB() {
        this(new Article());
    }

    public ArticleDB(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    @ShardAwareId
    public Long getArticleId() {
        return article.getArticleId();
    }

    @ShardGeneratedId(ShardGeneratedIdGroup.ARTICLE_ID_GROUP)
    public void setArticleId(Long articleId) {
        article.setArticleId(articleId);
    }

    public Integer getVersion() {
        return article.getVersion();
    }

    public void setVersion(Integer version) {
        article.setVersion(version);
    }

    public String getTitle() {
        return article.getTitle();
    }

    public void setTitle(String title) {
        article.setTitle(title);
    }

    public String getContent() {
        return article.getContent();
    }

    public void setContent(String content) {
        article.setContent(content);
    }

    public Date getCreateDate() {
        return article.getCreateDate();
    }

    public void setCreateDate(Date createDate) {
        article.setCreateDate(createDate);
    }

    public String getModifiedBy() {
        return article.getModifiedBy();
    }

    public void setModifiedBy(String modifiedBy) {
        article.setModifiedBy(modifiedBy);
    }

    public Date getModifiedDate() {
        return article.getModifiedDate();
    }

    public void setModifiedDate(Date modifiedDate) {
        article.setModifiedDate(modifiedDate);
    }

    public void setSiteId(Long siteId) {
        article.setSiteId(siteId);
    }

    @ShardAwareId
    public Long getSiteId() {
        return article.getSiteId();
    }

    @Override
    public String toString() {
        return "ArticleDB{" +
                "article=" + article +
                '}';
    }
}
