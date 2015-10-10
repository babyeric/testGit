package com.juric;

import com.practice.config.EnvironmentAwarePropertyConfigurer;
import com.practice.configuration.DBConfiguration;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import com.practice.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Eric on 10/8/2015.
 */
public class UserMapperTests extends AbstractMapperTest {

        @Autowired
        UserMapper userMapper;

        @Test
        public void testInsertUser() {
                UserDB userDB = createUserDB();
                int ret = userMapper.insert(userDB);
                Assert.assertEquals(1, ret);
                Assert.assertNotNull(userDB.getUserId());

                UserDB userDB2 = userMapper.getById(userDB.getUserId());
                Assert.assertEquals(userDB.toString(), userDB2.toString());
        }

        @Test
        public void testUpdateUser() {
                UserDB oldOne = createUserDB();
                int ret = userMapper.insert(oldOne);
                Assert.assertEquals(1, ret);
                Assert.assertNotNull(oldOne.getUserId());

                UserDB userDB = new UserDB();
                userDB.setUserId(oldOne.getUserId());
                userDB.setMobile("1234567");
                userDB.setLastName("lastName");
                userDB.setFirstName("firstName");
                userDB.setCountry("CN");
                userDB.setEmail("newEmail@test.com");
                userDB.setBirthday(DateUtils.parseDate("2010-12-21"));
                userDB.setModifiedBy("UnitTest");
                userDB.setModifiedDate(new Date());
                ret = userMapper.update(userDB);
                Assert.assertEquals(1, ret);

                UserDB userDB2 = userMapper.getById(userDB.getUserId());
                Assert.assertEquals(userDB.toString(), userDB2.toString());
        }

        @Test
        public void testGetByEmail() {
                UserDB oldOne = createUserDB();
                int ret = userMapper.insert(oldOne);
                Assert.assertEquals(1, ret);
                Assert.assertNotNull(oldOne.getUserId());
                UserDB userDB = userMapper.getByEmail(oldOne.getEmail());
                Assert.assertEquals(oldOne.toString(), userDB.toString());

                userDB = new UserDB();
                userDB.setUserId(oldOne.getUserId());
                userDB.setEmail("newEmail@test.com");
                ret = userMapper.update(userDB);
                Assert.assertEquals(1, ret);
                UserDB newOne = userMapper.getByEmail(userDB.getEmail());
                Assert.assertEquals(oldOne.getUserId(), newOne.getUserId());

                oldOne = userMapper.getByEmail(oldOne.getEmail());
                Assert.assertNull(oldOne);
        }

        @Test
        public void testGetByIdNotExist() {
                UserDB userDB = userMapper.getById(9999999999L);
                Assert.assertNull(userDB);
        }

        @Test
        public void testGetByEmailNotExist() {
                UserDB userDB = userMapper.getByEmail("NOT_EXIST@juric.com");
                Assert.assertNull(userDB);
        }

        private UserDB createUserDB() {
                UserDB userDB = new UserDB();
                userDB.setEmail(UUID.randomUUID() +"@juric.com");
                userDB.setBirthday(DateUtils.parseDate("2000-10-1"));
                userDB.setCountry("US");
                userDB.setFirstName("juric");
                userDB.setLastName("chen");
                userDB.setMobile("600400500");
                userDB.setModifiedBy("UT");
                userDB.setCreateDate(new Date());
                userDB.setModifiedDate(new Date());
                return userDB;
        }
}
