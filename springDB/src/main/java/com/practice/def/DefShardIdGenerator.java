package com.practice.def;

import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.strategy.IdStrategy;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created by Eric on 9/13/2015.
 */
public class DefShardIdGenerator {
    public final static int NEXT_SEQUENCE_STEP = 2;
    protected int batchSize;

    private IdGeneratorMapper idGeneratorMapper;

    public void setIdGeneratorMapper(IdGeneratorMapper idGeneratorMapper) {
        this.idGeneratorMapper = idGeneratorMapper;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public long generate(ShardGeneratedIdGroup idGroup, int logicalShardId) {
        long nextValue = doGenerateSequence(idGroup, logicalShardId);
        return convertSequenceToId(nextValue, logicalShardId);
    }

    protected long doGenerateSequence(ShardGeneratedIdGroup idGroup, int logicalShardId) {
        IdGeneratorParam param = new IdGeneratorParam();
        param.setGroupId(idGroup.value());
        param.setLogicalShardId(logicalShardId);
        param.setBatchSize(batchSize);
        idGeneratorMapper.getSequenceNextValue(param);
        return param.getNextValue();
    }

    protected long convertSequenceToId(long sequence, int logicalShardId) {
        Assert.isTrue(logicalShardId < IdStrategy.LOGICAL_SHARD_COUNT);
        return sequence * IdStrategy.LOGICAL_SHARD_COUNT + logicalShardId;
    }
}
