package com.practice.client.storage;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;

import java.io.File;

/**
 * Created by Eric on 9/28/2015.
 */
public interface StorageServiceClient {
    public File toFile(StoragePath storagePath);

    public StoragePath generateStoragePath(EnumRepository repo,
                                           EnumSchema schema,
                                           Long shardParam,
                                           String ext);
}
