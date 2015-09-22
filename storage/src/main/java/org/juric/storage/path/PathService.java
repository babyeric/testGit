package org.juric.storage.path;

import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PathService {
    public Path convertPath(LogicalPath logicalPath);

    public LogicalPath createPath(EnumRepository repo,
                                  EnumSchema schema,
                                  Integer logicalShardId,
                                  String ext);
}
