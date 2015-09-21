package org.juric.storage.config;

import org.juric.sharding.config.LogicalIdRange;
import org.juric.sharding.config.PhysicalRepository;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 11:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhysicalStorage extends PhysicalRepository {
    private final LogicalIdRange logicalIdRange;
    private final String root;

    public PhysicalStorage(LogicalIdRange logicalIdRange, String root) {
        super(logicalIdRange);
        this.logicalIdRange = logicalIdRange;
        this.root = root;
    }

    public String getRoot() {
        return root;
    }
}
