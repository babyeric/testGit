package org.juric.sharding.config;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalRepository {
    private final LogicalIdRange logicalIdRange;

    public PhysicalRepository(LogicalIdRange logicalIdRange) {
        this.logicalIdRange = logicalIdRange;
    }

    public final LogicalIdRange getLogicalIdRange() {
        return logicalIdRange;
    }
}
