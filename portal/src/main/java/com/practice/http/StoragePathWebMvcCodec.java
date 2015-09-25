package com.practice.http;

import org.apache.commons.lang.StringUtils;
import org.juric.storage.path.EnumRepository;
import org.juric.storage.path.EnumSchema;
import org.juric.storage.path.StoragePath;

/**
 * Created by Eric on 9/22/2015.
 */
public class StoragePathWebMvcCodec {
    public final static EnumRepository REPOSITORY = EnumRepository.PUBLIC;
    public final static String SEPARATOR = " ";
    public final static int NUM_PARTS = 3;
    public final static int INDEX_SCHEMA = 0;
    public final static int INDEX_LOGICALSHARDID = 1;
    public final static int INDEX_FILENAME = 2;


    public static StoragePath decode(String path) {
        if (StringUtils.isBlank(path)) {
            throw new IllegalArgumentException("path should not be blank");
        }

        path = StoragePathCodecUtils.decode(path);
        String[] parts = StringUtils.split(path, SEPARATOR);
        if (parts.length != NUM_PARTS) {
            throw new IllegalArgumentException("invalid path");
        }

        EnumSchema schema = EnumSchema.valueOf(parts[INDEX_SCHEMA].toUpperCase());
        int logicalShardId = Integer.parseInt(parts[INDEX_LOGICALSHARDID]);
        String filename = parts[INDEX_FILENAME];

        return new StoragePath(REPOSITORY, schema, logicalShardId, filename);
    }

    public static String encode(StoragePath storagePath) {
        if (storagePath.getRepo() != REPOSITORY) {
            throw new IllegalArgumentException("Invalid Repository");
        }

        String ret = StringUtils.join(new Object[]{storagePath.getSchema().name(), storagePath.getLogicalShardId(), storagePath.getSubPath()}, SEPARATOR);
        return StoragePathCodecUtils.encode(ret);
    }



}
