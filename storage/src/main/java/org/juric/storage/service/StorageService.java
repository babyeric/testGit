package org.juric.storage.service;


import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StorageService {
    public Path getPath(String repo,
            String schema,
            int logicalShardId,
            String localPath
    );
}
