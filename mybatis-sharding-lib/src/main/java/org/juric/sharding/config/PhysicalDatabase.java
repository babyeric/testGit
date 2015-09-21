package org.juric.sharding.config;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalDatabase extends PhysicalRepository{

    private final String host;
    private final String schema;

    public PhysicalDatabase(LogicalIdRange logicalIdRange, String host, String schema) {
        super(logicalIdRange);
        this.host = host;
        this.schema = schema;
    }

    public final String getHost() {
        return host;
    }

    public final String getSchema() {
        return schema;
    }
}
