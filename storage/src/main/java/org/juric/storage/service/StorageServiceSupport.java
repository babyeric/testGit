package org.juric.storage.service;

import com.practice.def.DefShardIdGenerator;
import org.apache.commons.lang.StringUtils;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.strategy.IdStrategy;
import org.juric.storage.config.PhysicalStorage;
import org.juric.storage.path.EnumRepository;
import org.juric.storage.path.EnumSchema;
import org.juric.storage.path.LogicalPath;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Eric on 9/22/2015.
 */
public abstract class StorageServiceSupport {
    public final static String DATE_FORMAT = "yyyyMMdd";

    protected RepositoryConfig<PhysicalStorage> storageConfig;
    private DefShardIdGenerator idGenerator;


    public void setStorageConfig(RepositoryConfig<PhysicalStorage> storageConfig) {
        this.storageConfig = storageConfig;
    }

    public void setIdGenerator(DefShardIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Path convertPath(LogicalPath logicalPath) {
        LogicalRepository<PhysicalStorage> logicalStorage = storageConfig.getLogicalRepository(logicalPath.getRepo().getName());
        int physicalShardId = logicalStorage.logicalToPhysicalId(logicalPath.getLogicalShardId());
        String root = logicalStorage.getPhysicalShard(physicalShardId).getRoot();
        return Paths.get(root,
                logicalPath.getSchema().getName(),
                String.valueOf(logicalPath.getLogicalShardId()),
                logicalPath.getSubPath());
    }

    public LogicalPath newPath(EnumRepository repo, EnumSchema schema, Integer logicalShardId, String ext) {
        if(logicalShardId == null) {
            logicalShardId = new Random().nextInt(IdStrategy.LOGICAL_SHARD_COUNT);
        }
        long newId = idGenerator.generate(schema.getIdGroup(), logicalShardId);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        String localPath = Paths.get(dateFormat.format(new Date()), String.valueOf(newId)+normalizeExtension(ext)).toString();

        return new LogicalPath(repo, schema, logicalShardId, localPath);
    }

    private String normalizeExtension(String ext) {
        if (ext == null || ext.isEmpty()) {
            return StringUtils.EMPTY;
        }

        String ret = ext.trim();
        if (!ret.startsWith(".")) {
            ret = "."+ret;
        }

        return ret;
    }
}
