package org.juric.storage.path;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogicalPath {
    private final EnumRepository repo;
    private final EnumSchema schema;
    private final int logicalShardId;
    private final String localPath;

    public LogicalPath(EnumRepository repo,
                       EnumSchema schema,
                       int logicalShardId,
                       String localPath) {
        this.repo = repo;
        this.schema = schema;
        this.logicalShardId = logicalShardId;
        this.localPath = localPath;
    }

    public EnumRepository getRepo() {
        return repo;
    }

    public EnumSchema getSchema() {
        return schema;
    }

    public int getLogicalShardId() {
        return logicalShardId;
    }

    public String getLocalPath() {
        return localPath;
    }
}
