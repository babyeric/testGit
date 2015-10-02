package com.juric.carbon.configuration;

import com.juric.carbon.service.storage.StoragePathService;
import com.juric.carbon.service.storage.StoragePathServiceImpl;
import com.practice.configuration.DBConfiguration;
import com.practice.def.DefShardIdGenerator;
import org.juric.storage.service.StorageService;
import org.juric.storage.service.StorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/1/15
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Import(DBConfiguration.class)
@Configuration
public class StorageConfiguration {

    private DefShardIdGenerator defShardIdGenerator;

    @Resource(name="defShardIdGenerator")
    public void setDefShardIdGenerator(DefShardIdGenerator defShardIdGenerator) {
        this.defShardIdGenerator = defShardIdGenerator;
    }

    @Bean(name="storagePathService")
    public StoragePathService storagePathService() {
        StoragePathServiceImpl storagePathService = new StoragePathServiceImpl();
        storagePathService.setIdGenerator(defShardIdGenerator);
        return storagePathService;
    }
}
