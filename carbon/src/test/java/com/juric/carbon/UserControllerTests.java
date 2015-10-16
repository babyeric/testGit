package com.juric.carbon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juric.carbon.configuration.CarbonConfiguration;
import com.juric.carbon.schema.user.User;
import com.juric.carbon.schema.user.UserCreate;
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

import java.util.Date;

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

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void createUser() throws Exception {
        User user = new User();
        user.setLastName("last name");
        user.setFirstName("first name");
        user.setMobile("11223344");
        user.setCountry("CN");
        user.setBirthday(new Date());
        user.setEmail("test@emial.com");
        user.setModifiedBy("UT");

        UserCreate userCreate = new UserCreate();
        userCreate.setUser(user);
        userCreate.setUserPassword("test password");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreate);
        MvcResult mvcResult = this.mockMvc.perform(post("/1/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andReturn();

        user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        Assert.assertNotNull(user.getUserId());
    }
}
