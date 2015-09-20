package org.juric.sharding.mapper;

import org.juric.sharding.config.DataSourceConfig;
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
    private static DataSourceConfig datasourceConfig = null;
    private static ShardingDataSourceManager dataSourceManager = null;

    public ShardingMapperUtils(DataSourceConfig datasourceConfig, ShardingDataSourceManager dataSourceManager) {
        ShardingMapperUtils.datasourceConfig = datasourceConfig;
        ShardingMapperUtils.dataSourceManager = dataSourceManager;
    }

    public static int logicalToPhysicalId(String logicalDbName, int logicalShardId) {
        return datasourceConfig.getLogicalDatabase(logicalDbName).logicalToPhysicalId(logicalShardId);
    }

    public static int physicalToLogicalId(String logicalDbName, int physicalShardId) {
        return datasourceConfig.getLogicalDatabase(logicalDbName).physicalToLogicalId(physicalShardId);
    }

    public static DataSource getDataSource(String logicalDbName, int physicalShardId) {
        return dataSourceManager.get(logicalDbName, physicalShardId);
    }

    public static int[] getPhysicalShardIds(String logicalDbName) {
        return datasourceConfig.getLogicalDatabase(logicalDbName).getPhysicalShardIds();
    }
}
