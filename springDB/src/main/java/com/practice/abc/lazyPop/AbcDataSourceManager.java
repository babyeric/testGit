package com.practice.abc.lazyPop;

import com.practice.abc.lazyPop.config.DataSourceConfig;
import com.practice.abc.lazyPop.config.LogicalDatabase;
import com.practice.abc.lazyPop.config.PhysicalShard;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcDataSourceManager {
    private DataSourceConfig dataSourceConfig;
    private ConcurrentHashMap<PhysicalShard, DataSource> dataSourceMap = new ConcurrentHashMap<PhysicalShard, DataSource>();

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public DataSource get(String logicalDbName, int physicalShardId) {
        PhysicalShard physicalShard = dataSourceConfig.getLogicalDatabase(logicalDbName).getPhysicalShard(physicalShardId);
        if (!dataSourceMap.containsKey(physicalShard)) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setUrl("jdbc:mysql://"+ physicalShard.getHost()+"/"+ physicalShard.getSchema());
            dataSource.setPassword("#Bugsfor$");
            dataSourceMap.putIfAbsent(physicalShard, dataSource);
        }
        return dataSourceMap.get(physicalShard);
    }
}
