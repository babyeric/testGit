package com.juric.carbon.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juric.carbon.schema.site.Site;
import com.juric.carbon.schema.user.User;
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
public class SiteControllerTests extends AbstractControllerTest  {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreateSite() throws Exception {
        Site site = createSite(null);
        Assert.assertNotNull(site.getSiteId());
    }

    @Test
    public void testUpdateSite() throws Exception {
        Site site = createSite(null);
        Assert.assertNotNull(site.getSiteId());

        Site update = new Site();
        update.setSiteId(site.getSiteId());
        update.setSiteTag("siteTag" + new Random().nextLong());
        update.setName("new name");
        update.setDescription("new desc");
        update.setModifiedBy("testUpdateSite");
        String content = objectMapper.writeValueAsString(update);
       mockMvc.perform(put("/1/sites")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        site = getSiteById(update.getSiteId());
        Assert.assertEquals(update.getSiteTag(), site.getSiteTag());
        Assert.assertEquals(update.getName(), site.getName());
        Assert.assertEquals(update.getDescription(), site.getDescription());
        Assert.assertEquals(update.getModifiedBy(), site.getModifiedBy());
    }

    @Test
    public void testSearchSiteByTag() throws Exception {
        Site site = createSite(null);
        Assert.assertNotNull(site.getSiteId());

        MvcResult mvcResult = mockMvc.perform(get("/1/sites?siteTag=" + site.getSiteTag())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Site result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Site.class);
        Assert.assertEquals(site.toString(), result.toString());
    }

    @Test
    public void testGetSitesByUserId() throws Exception {
        List<Site> sites = new ArrayList<Site>();
        long userId = Math.abs(new Random().nextLong()) ;
        sites.add(createSite(userId));
        sites.add(createSite(userId));

        MvcResult mvcResult = mockMvc.perform(get("/1/users/" + userId + "/sites")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Site> result =  objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Site>>(){});
        Assert.assertEquals(2, result.size());
        for (int i=0; i<2; ++i) {
            Assert.assertEquals(sites.get(i).toString(), result.get(i).toString());
        }
    }

    private Site getSiteById(long siteId) throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/1/sites/" + siteId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Site site = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Site.class);
        return site;
    }

    private Site createSite(Long userId) throws Exception {
        Site site = new Site();
        site.setUserId(userId==null?123456789L : userId);
        site.setName("test name");
        site.setSiteTag("siteTag" + new Random().nextLong());
        site.setDescription("site description");
        site.setModifiedBy("UT");

        String content = objectMapper.writeValueAsString(site);
        MvcResult mvcResult = this.mockMvc.perform(post("/1/sites")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        site = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Site.class);
        return site;
    }
}
