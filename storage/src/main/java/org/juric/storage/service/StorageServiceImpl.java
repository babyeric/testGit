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
public class StorageServiceImpl implements StorageService {


    @Override
    public File getFile(LogicalPath logicalPath) {
        return null;
    }

    @Override
    public File createFile(LogicalPath logicalPath) {
        return null;
    }

    @Override
    public LogicalPath newFilePath(EnumRepository repo, EnumSchema schema, Integer logicalShardId, String ext) {
        return null;
    }
}
