package com.practice.http;

import com.practice.db.DataService;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/10/15
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */

@RestController
public class Talkee {
    private final static Logger LOG = LoggerFactory.getLogger(Talkee.class);

    private DataService dataService;
    private UserMapper userMapper;

    @Resource(name="dataService2")
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Resource(name="userMapper")
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/test")
    String test() {
        return dataService.talk();
    }

    @RequestMapping(value="/test", method = RequestMethod.POST)
    String test(Long userId, String name, Date birthday) {
        UserDB userDB = new UserDB();
        userDB.setUserId(userId);
        userDB.setName(name);
        userDB.setBirthday(birthday);
        return String.valueOf(userMapper.insert(userDB));
    }
}
