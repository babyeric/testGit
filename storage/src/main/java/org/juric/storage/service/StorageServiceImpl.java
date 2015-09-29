package org.juric.storage.service;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;
import com.practice.def.DefShardIdGenerator;
import org.apache.commons.lang.StringUtils;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.strategy.IdStrategy;
import org.juric.storage.config.PhysicalStorage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class StorageServiceImpl extends StorageServiceSupport implements StorageService {

    @Override
    public File toFile(StoragePath storagePath) {
        Path filePath = convertPath(storagePath);
        File ret = filePath.toFile();
        return ret;
    }

    @Override
    public StoragePath generateStoragePath(EnumRepository repo, EnumSchema schema, Integer logicalShardId, String ext) {
        StoragePath ret = generatePath(repo, schema, logicalShardId, ext);
        File dir = toFile(ret).getParentFile();
        dir.mkdirs();
        return ret;
    }
}
