package org.juric.storage.service;

import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.storage.config.PhysicalStorage;

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
    private RepositoryConfig<PhysicalStorage> storageConfig;

    public void setStorageConfig(RepositoryConfig<PhysicalStorage> storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public Path getPath(String repo,
                        String schema,
                        int logicalShardId,
                        String localPath
    ) {
        LogicalRepository<PhysicalStorage> logicalStorage = storageConfig.getLogicalRepository(repo);
        int physicalShardId = logicalStorage.logicalToPhysicalId(logicalShardId);
        String root = logicalStorage.getPhysicalShard(physicalShardId).getRoot();
        return Paths.get(root, schema, String.valueOf(logicalShardId), localPath);
    }
}
