package com.practice.abc.lazyPop.config;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogicalDatabase {
    private final String name;
    private final TreeMap<LogicalIdRange, Integer> physicalShardMap = new TreeMap<>();
    private final List<PhysicalShard> physicalShards = new ArrayList<PhysicalShard>();

    public LogicalDatabase(String name) {
        this.name = name;
    }

    public void addPhysicalShard(PhysicalShard physicalShard) {
        if (physicalShardMap.containsKey(physicalShard.getLogicalIdRange())) {
            throw new IllegalArgumentException("conflict logicalIdRange");
        }
        physicalShardMap.put(physicalShard.getLogicalIdRange(), physicalShards.size());
        physicalShards.add(physicalShard);
    }

    public String getName() {
        return name;
    }

    public int physicalToLogicalId(int physicalShardId) {
        return physicalShards.get(physicalShardId).getLogicalIdRange().getLow();
    }

    public int logicalToPhysicalId(int logicalShardId) {
        return physicalShardMap.get(new LogicalIdRange(logicalShardId, logicalShardId));
    }

    public PhysicalShard getPhysicalShard(int physicalShardId) {
        return physicalShards.get(physicalShardId);
    }

    public Iterable<PhysicalShard> getPhysicalShards() {
        return physicalShards;
    }

    public int[] getPhysicalShardIds() {
        int[] ret = new int[physicalShards.size()];
        for(int i=0; i<ret.length; ++i) {
            ret[i] = i;
        }
        return ret;
    }
}
