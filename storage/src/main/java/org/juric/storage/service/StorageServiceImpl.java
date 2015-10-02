package org.juric.storage.service;

import com.juric.storage.path.StoragePath;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.storage.config.PhysicalStorage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StorageServiceImpl implements StorageService {
    protected RepositoryConfig<PhysicalStorage> storageConfig;

    public void setStorageConfig(RepositoryConfig<PhysicalStorage> storageConfig) {
        this.storageConfig = storageConfig;
    }


    @Override
    public File toFile(StoragePath storagePath) {
        Path filePath = convertPath(storagePath);
        File ret = filePath.toFile();
        return ret;
    }

    private Path convertPath(StoragePath storagePath) {
        LogicalRepository<PhysicalStorage> logicalStorage = storageConfig.getLogicalRepository(storagePath.getRepo().name());
        int physicalShardId = logicalStorage.logicalToPhysicalId(storagePath.getLogicalShardId());
        String root = logicalStorage.getPhysicalShard(physicalShardId).getRoot();
        return Paths.get(root,
                storagePath.getSchema().name(),
                String.valueOf(storagePath.getLogicalShardId()),
                storagePath.getSubPath());
    }

}
