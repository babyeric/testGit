package org.juric.storage.service;

import org.juric.storage.path.EnumRepository;
import org.juric.storage.path.EnumSchema;
import org.juric.storage.path.LogicalPath;

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
    public File getFile(LogicalPath logicalPath);

    public File createFile(LogicalPath logicalPath);

    public LogicalPath newFilePath(EnumRepository repo,
                                  EnumSchema schema,
                                  Integer logicalShardId,
                                  String ext);
}
