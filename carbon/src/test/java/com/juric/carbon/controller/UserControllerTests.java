package com.juric.carbon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juric.carbon.configuration.CarbonConfiguration;
import com.juric.carbon.controller.AbstractControllerTest;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
import com.practice.utils.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/16/15
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */

public class UserControllerTests extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());

        User update = new User();
        update.setUserId(user.getUserId());
        update.setEmail("update@email.com");
        update.setModifiedBy("testUpdateUser");

        String content = objectMapper.writeValueAsString(update);

        mockMvc.perform(put("/1/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetUserById() throws Exception {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());

        String url = "/1/users/" + user.getUserId();
        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andReturn();

        User result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertEquals(user.toString(), result.toString());
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        User user = createUser();
        Assert.assertNotNull(user.getUserId());

        String url = "/1/users/?email=" + user.getEmail();
        MvcResult mvcResult = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        User result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertEquals(user.toString(), result.toString());
    }

    private User createUser() throws Exception{
        User user = new User();
        user.setLastName("last name");
        user.setFirstName("first name");
        user.setMobile("11223344");
        user.setCountry("CN");
        user.setBirthday(DateUtils.parseDate("2001-12-13"));
        user.setEmail("test" + new Random().nextLong()+"@email.com");
        user.setModifiedBy("UT");

        UserCreate userCreate = new UserCreate();
        userCreate.setUser(user);
        userCreate.setUserPassword("test password");
        String content = objectMapper.writeValueAsString(userCreate);
        MvcResult mvcResult = this.mockMvc.perform(post("/1/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        return user;
    }
}
