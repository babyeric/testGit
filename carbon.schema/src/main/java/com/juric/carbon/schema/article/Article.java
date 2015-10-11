package com.juric.carbon.schema.article;

import com.juric.carbon.schema.base.BaseSchema;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/2/15
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Article extends BaseSchema {
    private Long articleId;
    private Long siteId;
    private Integer version;
    private String title;
    private String content;

    public Long getArticleId() {
        return articleId;
    }

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

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", siteId=" + siteId +
                ", version=" + version +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                '}';
    }
}
