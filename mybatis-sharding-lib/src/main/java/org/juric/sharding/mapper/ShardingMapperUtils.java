package org.juric.sharding.mapper;

import org.juric.sharding.config.PhysicalDatabase;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.datasource.ShardingDataSourceManager;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingMapperUtils {
    private static RepositoryConfig<PhysicalDatabase> repositoryConfig = null;
    private static ShardingDataSourceManager dataSourceManager = null;

    public ShardingMapperUtils(RepositoryConfig<PhysicalDatabase> repositoryConfig, ShardingDataSourceManager dataSourceManager) {
        ShardingMapperUtils.repositoryConfig = repositoryConfig;
        ShardingMapperUtils.dataSourceManager = dataSourceManager;
    }

    public static int logicalToPhysicalId(String logicalDbName, int logicalShardId) {
        return repositoryConfig.getLogicalRepository(logicalDbName).logicalToPhysicalId(logicalShardId);
    }

    public static int physicalToLogicalId(String logicalDbName, int physicalShardId) {
        return repositoryConfig.getLogicalRepository(logicalDbName).physicalToLogicalId(physicalShardId);
    }

    public static DataSource getDataSource(String logicalDbName, int physicalShardId) {
        return dataSourceManager.get(logicalDbName, physicalShardId);
    }

    public static int[] getPhysicalShardIds(String logicalDbName) {
        return repositoryConfig.getLogicalRepository(logicalDbName).getPhysicalShardIds();
    }
}
