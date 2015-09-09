package com.practice.abc.lazyPop.strategy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class StrategyResult {
    private int logicalShardId;
    private int[] physicalShardIds;

    public StrategyResult(int logicalShardId, int[] physicalShardIds) {
        this.logicalShardId = logicalShardId;
        this.physicalShardIds = physicalShardIds;
    }

    public int getLogicalShardId() {
        return logicalShardId;
    }

    public int[] getPhysicalShardIds() {
        return physicalShardIds;
    }

}
