package org.juric.sharding.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyResult {
    private int[] logicalShardIds;
    private int[] physicalShardIds;

    public StrategyResult(int[] logicalShardIds, int[] physicalShardIds) {
        this.logicalShardIds = logicalShardIds;
        this.physicalShardIds = physicalShardIds;
    }

    public int[] getLogicalShardIds() {
        return logicalShardIds;
    }

    public int[] getPhysicalShardIds() {
        return physicalShardIds;
    }

}
