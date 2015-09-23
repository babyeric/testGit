package com.practice.http;

import com.practice.db.DataService;
import com.practice.user.UserDB;
import com.practice.user.UserMapper;
import org.juric.storage.configurer.StorageConfiguration;
import org.juric.storage.path.EnumRepository;
import org.juric.storage.path.EnumSchema;
import org.juric.storage.path.StoragePath;
import org.juric.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/10/15
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */

@Import(StorageConfiguration.class)
@RestController
public class Talkee {
    private final static Logger LOG = LoggerFactory.getLogger(Talkee.class);

    private DataService dataService;
    private UserMapper userMapper;
    private StorageService storageService;

    @Resource(name="dataService2")
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Resource(name="userMapper")
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Resource(name="storageService")
    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping("/test")
    String test() {
        StoragePath storagePath = storageService.generateFilePath(EnumRepository.PUBLIC, EnumSchema.IMAGE, null, ".txt");
        File file = storageService.toFile(storagePath);
        if(!file.exists()) {
            try {
                file.createNewFile();
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
                writer.write("test file");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


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
