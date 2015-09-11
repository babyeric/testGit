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
    private final String schema;

    public PhysicalShard(LogicalIdRange logicalIdRange, String host, String schema) {
        this.logicalIdRange = logicalIdRange;
        this.host = host;
        this.schema = schema;
    }

    public final LogicalIdRange getLogicalIdRange() {
        return logicalIdRange;
    }

    public final String getHost() {
        return host;
    }

    public final String getSchema() {
        return schema;
    }
}
