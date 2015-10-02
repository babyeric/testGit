package com.juric.carbon.service.storage;

import com.juric.storage.path.EnumRepository;
import com.juric.storage.path.EnumSchema;
import com.juric.storage.path.StoragePath;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/1/15
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface StoragePathService {
    public StoragePath generatePath(EnumRepository repo,
                                           EnumSchema schema,
                                           Long shardParam,
                                           String ext);
}
