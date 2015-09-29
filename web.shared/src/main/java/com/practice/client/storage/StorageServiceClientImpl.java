package com.practice.client.storage;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;

import java.io.File;

/**
 * Created by Eric on 9/28/2015.
 */
public class StorageServiceClientImpl implements StorageServiceClient {

    @Override
    public File toFile(StoragePath storagePath) {
        return null;
    }

    @Override
    public StoragePath generateStoragePath(EnumRepository repo, EnumSchema schema, Integer logicalShardId, String ext) {
        return null;
    }
}
