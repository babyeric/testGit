package com.juric.carbon.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
    public void testInsertArticle() throws Exception {
        Article article = createArticle(null);
        Assert.assertNotNull(article.getArticleId());
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
