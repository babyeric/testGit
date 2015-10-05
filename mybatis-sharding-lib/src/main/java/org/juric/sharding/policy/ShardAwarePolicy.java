package org.juric.sharding.policy;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/5/15
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ShardAwarePolicy {
    Integer resolveLogicalShardId(Object arg);
}
