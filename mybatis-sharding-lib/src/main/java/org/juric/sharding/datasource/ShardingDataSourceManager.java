package org.juric.sharding.datasource;

import org.juric.sharding.config.PhysicalDatabase;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.transactional.spring.ShardingDataSourceProxy;

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
    private RepositoryConfig<PhysicalDatabase> repositoryConfig;
    private DataSourceFactory dataSourceFactory;

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
    private ConcurrentHashMap<PhysicalDatabase, DataSource> dataSourceMap = new ConcurrentHashMap<PhysicalDatabase, DataSource>();

    public void setRepositoryConfig(RepositoryConfig<PhysicalDatabase> repositoryConfig) {
        this.repositoryConfig = repositoryConfig;
    }

    public DataSource get(String logicalDbName, int physicalShardId) {
        PhysicalDatabase physicalShard = repositoryConfig.getLogicalRepository(logicalDbName).getPhysicalShard(physicalShardId);
        if (!dataSourceMap.containsKey(physicalShard)) {
            DataSource dataSource = dataSourceFactory.createDataSource(physicalShard.getHost(), physicalShard.getSchema());
            dataSourceMap.putIfAbsent(physicalShard, new ShardingDataSourceProxy(dataSource));
        }
        return dataSourceMap.get(physicalShard);
    }
}
