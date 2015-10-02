package com.juric.storage.path;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class StoragePath {
    public final static String SEPARATOR = "/";
    private EnumRepository repo;
    private EnumSchema schema;
    private int logicalShardId;
    private String subPath;

    public StoragePath() {

    }

    public StoragePath(EnumRepository repo,
                       EnumSchema schema,
                       int logicalShardId,
                       String subPath) {
        this.repo = repo;
        this.schema = schema;
        this.logicalShardId = logicalShardId;
        this.subPath = subPath.replace("\\", SEPARATOR);
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

    public String getSubPath() {
        return subPath;
    }

    public void setRepo(EnumRepository repo) {
        this.repo = repo;
    }

    public void setSchema(EnumSchema schema) {
        this.schema = schema;
    }

    public void setLogicalShardId(int logicalShardId) {
        this.logicalShardId = logicalShardId;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }
}
