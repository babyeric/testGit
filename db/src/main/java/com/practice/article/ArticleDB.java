package com.practice.article;

import com.practice.def.ShardGeneratedIdGroup;
import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;

import java.util.Date;

/**
 * Created by Eric on 9/27/2015.
 */
public class ArticleDB {
    private Long articleId;
    private Integer version;
    private String title;
    private String content;
    private Date createDate;
    private String createBy;

    @ShardAwareId
    public Long getArticleId() {
        return articleId;
    }

    @ShardGeneratedId(ShardGeneratedIdGroup.ARTICLE_ID_GROUP)
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
