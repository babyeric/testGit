package com.practice.def;

import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/15/15
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdGeneratorParam {
    private Integer logicalShardId;
    private Integer groupId;
    private Integer batchSize;
    private Long nextValue;

    @ShardAwareId
    public Integer getLogicalShardId() {
        return logicalShardId;
    }

    public void setLogicalShardId(Integer logicalShardId) {
        this.logicalShardId = logicalShardId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Long getNextValue() {
        return nextValue;
    }

    public void setNextValue(Long nextValue) {
        this.nextValue = nextValue;
    }
}
