package org.juric.storage.service;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;

import java.io.File;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StorageService {
    public File toFile(StoragePath storagePath);

    public StoragePath generateStoragePath(EnumRepository repo,
                                  EnumSchema schema,
                                  Long shardParam,
                                  String ext);
}
