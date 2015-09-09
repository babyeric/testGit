package com.practice.abc.lazyPop.config;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalShard {
    private final LogicalIdRange logicalIdRange;
    private final String host;
    private final String database;

    public PhysicalShard(LogicalIdRange logicalIdRange, String host, String database) {
        this.logicalIdRange = logicalIdRange;
        this.host = host;
        this.database = database;
    }

    public final LogicalIdRange getLogicalIdRange() {
        return logicalIdRange;
    }

    public final String getHost() {
        return host;
    }

    public final String getDatabase() {
        return database;
    }
}
