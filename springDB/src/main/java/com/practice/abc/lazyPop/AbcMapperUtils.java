package com.practice.abc.lazyPop;

import com.practice.abc.lazyPop.config.DataSourceConfig;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbcMapperUtils {
    private static DataSourceConfig datasourceConfig = null;
    private static AbcDataSourceManager dataSourceManager = null;

    public AbcMapperUtils(DataSourceConfig datasourceConfig, AbcDataSourceManager dataSourceManager) {
        AbcMapperUtils.datasourceConfig = datasourceConfig;
        AbcMapperUtils.dataSourceManager = dataSourceManager;
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
