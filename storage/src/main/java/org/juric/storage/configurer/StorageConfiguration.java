package org.juric.storage.configurer;

import com.practice.configuration.DBConfiguration;
import com.practice.def.DefShardIdGenerator;
import org.juric.sharding.config.LogicalIdRange;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.PhysicalDatabase;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.storage.config.PhysicalStorage;
import org.juric.storage.path.EnumRepository;
import org.juric.storage.service.StorageService;
import org.juric.storage.service.StorageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/22/15
 * Time: 11:13 AM
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

    @Bean
    public RepositoryConfig<PhysicalStorage> storageConifg() {
        RepositoryConfig<PhysicalStorage> storageConfig = new RepositoryConfig();
        LogicalRepository<PhysicalStorage> logicalStorage = new LogicalRepository(EnumRepository.PUBLIC.name());
        storageConfig.addLogicalRepository(logicalStorage);
        logicalStorage.addPhysicalRepository(new PhysicalStorage(new LogicalIdRange(0, 49999), "D:\\storage\\p1"));
        logicalStorage.addPhysicalRepository(new PhysicalStorage(new LogicalIdRange(50000, 99999), "D:\\storage\\p2"));
        return storageConfig;
    }

    @Bean(name="storageService")
    public StorageService storageService() {
        StorageServiceImpl storageService = new StorageServiceImpl();
        storageService.setIdGenerator(defShardIdGenerator);
        storageService.setStorageConfig(storageConifg());
        return storageService;
    }
}
