package com.juric.carbon.schema.article;

import com.juric.carbon.schema.base.BaseSchema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(max=100)
    private String title;
    @NotNull
    @Size(max=4000)
    private String content;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + getCreateDate() + '\'' +
                '}';
    }
}
