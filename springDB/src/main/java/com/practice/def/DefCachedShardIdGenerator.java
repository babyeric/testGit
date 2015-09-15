package com.practice.def;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Eric on 9/14/2015.
 */
public class DefCachedShardIdGenerator extends DefShardIdGenerator{
    private static class IdGenCache {
        private long cur;
        private long limit;

        public IdGenCache(long nextSequence, int cacheSize) {
            this.cur = nextSequence;
            this.limit = nextSequence + cacheSize * NEXT_SEQUENCE_STEP;
        }

        public boolean isEmpty() {
            if (cur < limit) {
                return false;
            }

            return true;
        }

        public long next() {
            Assert.isTrue(!isEmpty());
            long ret = cur;
            cur += NEXT_SEQUENCE_STEP;
            return ret;
        }
    }
    private Map<ShardGeneratedIdGroup, Map<Integer, IdGenCache>> idGenCacheMap = new ConcurrentHashMap<>();

    public long generate(ShardGeneratedIdGroup idGroup, int logicalShardId) {
        if (batchSize == 1) {
            return  super.generate(idGroup, logicalShardId);
        }

        long nextValue = nextSequenceFromCache(idGroup, logicalShardId);
        return convertSequenceToId(nextValue, logicalShardId);
    }

    private long nextSequenceFromCache(ShardGeneratedIdGroup idGroup, int logicalShardId) {
        Map<Integer, IdGenCache> cacheMap =  idGenCacheMap.get(idGroup);
        if (cacheMap == null) {
            synchronized (idGenCacheMap) {
                cacheMap =  idGenCacheMap.get(idGroup);
                if (cacheMap == null) {
                    cacheMap = new ConcurrentHashMap<>();
                    idGenCacheMap.put(idGroup, cacheMap);
                }
            }
        }

        IdGenCache idGenCache = cacheMap.get(logicalShardId);
        if (idGenCache == null || idGenCache.isEmpty()) {
            synchronized (cacheMap) {
                idGenCache = cacheMap.get(logicalShardId);
                if (idGenCache == null || idGenCache.isEmpty()) {
                    long nextSequence = doGenerateSequence(idGroup, logicalShardId);
                    idGenCache = new IdGenCache(nextSequence, batchSize);
                    cacheMap.put(logicalShardId, idGenCache);
                }
            }
        }

        return idGenCache.next();
    }


}
