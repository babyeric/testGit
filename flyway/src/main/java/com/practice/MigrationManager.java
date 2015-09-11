package com.practice;

import com.practice.abc.lazyPop.AbcDataSourceManager;
import com.practice.abc.lazyPop.config.DataSourceConfig;
import com.practice.abc.lazyPop.config.LogicalDatabase;
import com.practice.abc.lazyPop.config.PhysicalShard;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 9/10/2015.
 */
public class MigrationManager {
    private final static String SCRIPT_ROOT = "db/migration";
    private final static String COLLATE = "utf8_bin";
    private final static String CREATE_SCHEMA_TEMPLATE = "CREATE DATABASE IF NOT EXISTS `%s` /*!40100 COLLATE %s */";

    private final Map<String, Connection> connectionMap = new HashMap<>();
    DataSourceConfig dataSourceConfig;
    AbcDataSourceManager dataSourceManager;

    @Resource(name = "dataSourceConfig")
    public void setDataSourceConfig(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Resource(name = "dataSourceManager")
    public void setDataSourceManager(AbcDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    public void migrate() {
        try {
            for (LogicalDatabase logicalDatabase : dataSourceConfig.getAllLogicalDatabase()) {
                for(int physicalShardId : logicalDatabase.getPhysicalShardIds()) {
                    PhysicalShard physicalShard = logicalDatabase.getPhysicalShard(physicalShardId);
                    createSchemaIfNotExist(physicalShard);
                    DataSource dataSource = dataSourceManager.get(logicalDatabase.getName(), physicalShardId);
                    migrate(logicalDatabase.getName(), dataSource);
                }
            }

            for(String host : connectionMap.keySet()) {
                connectionMap.get(host).close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            connectionMap.clear();
        }
    }

    private void createSchemaIfNotExist(PhysicalShard physicalShard) throws SQLException {
        if (!connectionMap.containsKey(physicalShard.getHost())) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setUrl("jdbc:mysql://" + physicalShard.getHost());
            dataSource.setPassword("#Bugsfor$");
            connectionMap.put(physicalShard.getHost(), dataSource.getConnection());
        }

        Statement statement = connectionMap.get(physicalShard.getHost()).createStatement();
        statement.execute(String.format(CREATE_SCHEMA_TEMPLATE, physicalShard.getSchema() , COLLATE));

    }

    private void migrate(String logicalDbName, DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setLocations(new String[]{SCRIPT_ROOT + "/" + logicalDbName});
        flyway.setDataSource(dataSource);
        flyway.setValidateOnMigrate(true);
        flyway.setEncoding("utf-8");
        flyway.migrate();
    }
}
