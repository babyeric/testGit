package com.juric;

import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;
import com.practice.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 10/10/2015.
 */
public class ArticleMapperTests extends AbstractMapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testInsertArticle() {
        ArticleDB articleDB = articleDB(12345678L, null);
        int ret = articleMapper.insert(articleDB);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB.getVersion().intValue());
        Assert.assertNotNull(articleDB.getArticleId());

        ArticleDB result = articleMapper.getById(articleDB.getArticleId());
        Assert.assertEquals(articleDB.toString(), result.toString());

        articleDB = articleDB(12345678L, null);
        articleDB.setArticleId(result.getArticleId());
        ret = articleMapper.insert(articleDB);

        Assert.assertEquals(1, ret);
        Assert.assertEquals(2, articleDB.getVersion().intValue());
        Assert.assertNotNull(articleDB.getArticleId());

        result = articleMapper.getById(articleDB.getArticleId());
        Assert.assertEquals(articleDB.toString(), result.toString());
    }

    @Test
    public void testGetBySiteDifferentDate() {
        long siteId = 12345678L;
        Date d1 = DateUtils.parseDateTime("2010-01-01 06:08:07");
        Date d2 = DateUtils.parseDateTime("2010-01-01 09:08:07");

        ArticleDB articleDB1 = articleDB(siteId, d1);
        int ret = articleMapper.insert(articleDB1);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB1.getVersion().intValue());
        Assert.assertNotNull(articleDB1.getArticleId());

        ArticleDB articleDB2 = articleDB(siteId, d2);
        ret = articleMapper.insert(articleDB2);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB2.getVersion().intValue());
        Assert.assertNotNull(articleDB2.getArticleId());

        List<ArticleDB> articles = articleMapper.getBySite(siteId, null, null, 1);
        Assert.assertEquals(1, articles.size());
        Assert.assertEquals(articleDB1.toString(), articles.get(0).toString());

        articles = articleMapper.getBySite(siteId, articleDB1.getCreateDate(), articleDB1.getArticleId(), 1);
        Assert.assertEquals(1, articles.size());
        Assert.assertEquals(articleDB2.toString(), articles.get(0).toString());
    }

    @Test
    public void testGetBySiteSameDate() {
        long siteId = 12345678L;
        Date date = DateUtils.parseDateTime("2010-01-01 09:08:07");
        ArticleDB articleDB1 = articleDB(12345678L, date);
        int ret = articleMapper.insert(articleDB1);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB1.getVersion().intValue());
        Assert.assertNotNull(articleDB1.getArticleId());

        ArticleDB articleDB2 = articleDB(12345678L, date);
        ret = articleMapper.insert(articleDB2);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB2.getVersion().intValue());
        Assert.assertNotNull(articleDB2.getArticleId());

        if (articleDB1.getArticleId() > articleDB2.getArticleId()) {
            ArticleDB temp = articleDB1;
            articleDB1 = articleDB2;
            articleDB2 = temp;
        }

        List<ArticleDB> articles = articleMapper.getBySite(siteId, null, null, 1);
        Assert.assertEquals(1, articles.size());
        Assert.assertEquals(articleDB1.toString(), articles.get(0).toString());

        articles = articleMapper.getBySite(siteId, articleDB1.getCreateDate(), articleDB1.getArticleId(), 1);
        Assert.assertEquals(1, articles.size());
        Assert.assertEquals(articleDB2.toString(), articles.get(0).toString());
    }

    private ArticleDB articleDB(long siteId, Date createDate) {
        ArticleDB articleDB = new ArticleDB();
        articleDB.setTitle("it's a title");
        articleDB.setContent("it's my content");
        articleDB.setSiteId(siteId);
        articleDB.setModifiedBy("UT");
        articleDB.setCreateDate(createDate == null? new Date() : createDate);
        articleDB.setModifiedDate(new Date());
        articleDB.setModifiedBy("BY");
        return articleDB;
    }



}
