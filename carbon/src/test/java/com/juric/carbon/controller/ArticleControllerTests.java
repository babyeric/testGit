package com.juric.carbon.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.site.Site;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Eric on 10/17/2015.
 */
public class ArticleControllerTests extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreateArticle() throws Exception {
        Article article = createArticle(null);
        Assert.assertNotNull(article.getArticleId());
    }

    @Test
    public void testUpdateArticle() throws Exception {
        Article article = createArticle(null);
        Assert.assertNotNull(article.getArticleId());

        Article update = new Article();
        update.setArticleId(article.getArticleId());
        update.setContent("updated content");
        update.setTitle("updated title");
        update.setModifiedBy("testSaveExistingArticle");

        String content = objectMapper.writeValueAsString(update);
        MvcResult mvcResult = this.mockMvc.perform(put("/1/articles")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Article result = getById(update.getArticleId());
        Assert.assertEquals(update.getContent(), result.getContent());
        Assert.assertEquals(update.getTitle(), result.getTitle());
        Assert.assertEquals(update.getModifiedBy(), result.getModifiedBy());
    }

    @Test
    public void testGetArticlesBySite() throws Exception {
        long siteId = Math.abs(new Random().nextLong());
        List<Article> articleList = new ArrayList<Article>();

        articleList.add(createArticle(siteId));
        articleList.add(createArticle(siteId));

        MvcResult mvcResult = this.mockMvc.perform(get("/1/sites/" + siteId + "/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("pageSize", 2))
                .andExpect(status().isOk())
                .andReturn();

        List<Article> result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Article>>(){});
        Assert.assertEquals(2, result.size());
        for (int i=0; i<2; ++i) {
            Assert.assertEquals(articleList.get(i).toString(), result.get(i).toString());
        }

        mvcResult = this.mockMvc.perform(get("/1/sites/" + siteId + "/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("pageSize", 1))
                .andExpect(status().isOk())
                .andReturn();

        result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Article>>() {});
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(articleList.get(0).toString(), result.get(0).toString());

        mvcResult = this.mockMvc.perform(get("/1/sites/" + siteId + "/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("pageSize", 1)
                .header("lastDate", result.get(0).getCreateDate())
                .header("lastId", result.get(0).getArticleId()))
                .andExpect(status().isOk())
                .andReturn();

        result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Article>>() {});
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(articleList.get(1).toString(), result.get(0).toString());
    }

    private Article getById(long articleId) throws  Exception{
        MvcResult mvcResult = this.mockMvc.perform(get("/1/articles/" + articleId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article.class);
    }

    private Article createArticle(Long siteId) throws Exception {
        Article article = new Article();
        article.setSiteId(siteId == null ? 12345678L : siteId);
        article.setContent("test content");
        article.setTitle("test title");
        article.setModifiedBy("UT");

        String content = objectMapper.writeValueAsString(article);
        MvcResult mvcResult = this.mockMvc.perform(post("/1/articles")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        article = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Article.class);
        return article;
    }
}
