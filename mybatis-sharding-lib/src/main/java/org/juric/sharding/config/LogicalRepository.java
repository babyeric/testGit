package org.juric.sharding.config;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogicalRepository<T extends PhysicalRepository> {
    private final String name;
    private final TreeMap<org.juric.sharding.config.LogicalIdRange, Integer> physicalRepositryMap = new TreeMap<org.juric.sharding.config.LogicalIdRange, Integer>();
    private final List<T> physicalRepositories = new ArrayList<>();

    public LogicalRepository(String name) {
        this.name = name;
    }

    public void addPhysicalShard(T physicalShard) {
        if (physicalRepositryMap.containsKey(physicalShard.getLogicalIdRange())) {
            throw new IllegalArgumentException("conflict logicalIdRange");
        }
        physicalRepositryMap.put(physicalShard.getLogicalIdRange(), physicalRepositories.size());
        physicalRepositories.add(physicalShard);
    }

    public String getName() {
        return name;
    }

    public int physicalToLogicalId(int physicalShardId) {
        return physicalRepositories.get(physicalShardId).getLogicalIdRange().getLow();
    }

    public int logicalToPhysicalId(int logicalShardId) {
        return physicalRepositryMap.get(new LogicalIdRange(logicalShardId, logicalShardId));
    }

    public T getPhysicalShard(int physicalShardId) {
        return physicalRepositories.get(physicalShardId);
    }

    public Iterable<T> getPhysicalShards() {
        return physicalRepositories;
    }

    public int[] getPhysicalShardIds() {
        int[] ret = new int[physicalRepositories.size()];
        for(int i=0; i<ret.length; ++i) {
            ret[i] = i;
        }
        return ret;
    }
}
