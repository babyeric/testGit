package com.juric;

import com.practice.config.EnvironmentAwarePropertyConfigurer;
import com.practice.configuration.DBConfiguration;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.util.CollectionUtils;

/**
 * Created by Eric on 10/8/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestApplication.class, DBConfiguration.class}, initializers = ConfigFileApplicationContextInitializer.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class UserMapperTests {

        @Autowired
        UserMapper userMapper;

        @Test
        public void testInsertUser() {
                UserDB userDB = new UserDB();
                userMapper.insert(userDB);
        }
}
