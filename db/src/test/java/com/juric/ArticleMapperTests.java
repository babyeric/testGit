package com.juric;

import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Eric on 10/10/2015.
 */
public class ArticleMapperTests extends AbstractMapperTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testInsertArticle() {
        ArticleDB articleDB = articleDB(12345678L);
        int ret = articleMapper.insert(articleDB);
        Assert.assertEquals(1, ret);
        Assert.assertEquals(1, articleDB.getVersion().intValue());
        Assert.assertNotNull(articleDB.getArticleId());

        ArticleDB result = articleMapper.getById(articleDB.getArticleId());
        Assert.assertEquals(articleDB.toString(), result.toString());

        articleDB = articleDB(12345678L);
        articleDB.setArticleId(result.getArticleId());
        ret = articleMapper.insert(articleDB);

        Assert.assertEquals(1, ret);
        Assert.assertEquals(2, articleDB.getVersion().intValue());
        Assert.assertNotNull(articleDB.getArticleId());

        result = articleMapper.getById(articleDB.getArticleId());
        Assert.assertEquals(articleDB.toString(), result.toString());
    }

    @Test
    public void testGetBySite() {

    }

    private ArticleDB articleDB(long siteId) {
        ArticleDB articleDB = new ArticleDB();
        articleDB.setTitle("it's a title");
        articleDB.setContent("it's my content");
        articleDB.setSiteId(siteId);
        articleDB.setModifiedBy("UT");
        articleDB.setCreateDate(new Date());
        articleDB.setModifiedDate(new Date());
        articleDB.setModifiedBy("BY");
        return articleDB;
    }



}
