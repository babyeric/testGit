package com.juric.carbon.service.storage;

import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.schema.storage.path.EnumRepository;
import com.juric.carbon.schema.storage.path.EnumSchema;
import com.juric.carbon.schema.storage.path.StoragePath;
import com.practice.def.DefShardIdGenerator;
import com.practice.def.ShardGeneratedIdGroup;
import org.apache.commons.lang.StringUtils;
import org.juric.sharding.strategy.IdStrategy;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/1/15
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class StoragePathServiceImpl implements StoragePathService {
    public final static String DATE_FORMAT = "yyyyMMdd";
    private final static Map<EnumSchema, ShardGeneratedIdGroup> SCHEMA_TO_IDGROUP = new HashMap<>();

    static {
        SCHEMA_TO_IDGROUP.put(EnumSchema.IMAGE, ShardGeneratedIdGroup.IMAGE_FILE_ID_GROUP);
    }

    private DefShardIdGenerator idGenerator;

    public void setIdGenerator(DefShardIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public StoragePath generatePath(EnumRepository repo, EnumSchema schema, Long shardParam, String ext) {
        int logicalShardId = logicalShardId(shardParam);
        long newId = idGenerator.generate(SCHEMA_TO_IDGROUP.get(schema), logicalShardId);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        String localPath = Paths.get(dateFormat.format(new Date()), String.valueOf(newId) + normalizeExtension(ext)).toString();

        return new StoragePath(repo, schema, logicalShardId, localPath);
    }


    private int logicalShardId(Long shardParam) {
        if (shardParam == null) {
            return new Random().nextInt(IdStrategy.LOGICAL_SHARD_COUNT);
        } else {
            return (int)(shardParam % IdStrategy.LOGICAL_SHARD_COUNT);
        }
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
