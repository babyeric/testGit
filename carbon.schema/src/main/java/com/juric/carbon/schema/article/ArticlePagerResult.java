package com.juric.carbon.schema.article;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/21/15
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArticlePagerResult {
    List<Article> articles;

    Date forwardDate;
    Long forwardId;

    Date backwardDate;
    Long backwardId;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Date getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Date forwardDate) {
        this.forwardDate = forwardDate;
    }

    public Long getForwardId() {
        return forwardId;
    }

    public void setForwardId(Long forwardId) {
        this.forwardId = forwardId;
    }

    public Date getBackwardDate() {
        return backwardDate;
    }

    public void setBackwardDate(Date backwardDate) {
        this.backwardDate = backwardDate;
    }

    public Long getBackwardId() {
        return backwardId;
    }

    public void setBackwardId(Long backwardId) {
        this.backwardId = backwardId;
    }
}
