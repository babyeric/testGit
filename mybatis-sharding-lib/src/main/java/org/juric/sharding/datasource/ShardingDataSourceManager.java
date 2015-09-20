package org.juric.sharding.datasource;

import org.juric.sharding.config.DataSourceConfig;
import org.juric.sharding.config.PhysicalShard;
import org.juric.sharding.transactional.spring.ShardingDataSourceProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingDataSourceManager {
    private DataSourceConfig dataSourceConfig;
    private DataSourceFactory dataSourceFactory;

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
    private ConcurrentHashMap<PhysicalShard, DataSource> dataSourceMap = new ConcurrentHashMap<PhysicalShard, DataSource>();

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public DataSource get(String logicalDbName, int physicalShardId) {
        PhysicalShard physicalShard = dataSourceConfig.getLogicalDatabase(logicalDbName).getPhysicalShard(physicalShardId);
        if (!dataSourceMap.containsKey(physicalShard)) {
            DataSource dataSource = dataSourceFactory.createDataSource(physicalShard.getHost(), physicalShard.getSchema());
            dataSourceMap.putIfAbsent(physicalShard, new ShardingDataSourceProxy(dataSource));
        }
        return dataSourceMap.get(physicalShard);
    }
}
