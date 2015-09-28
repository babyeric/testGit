package com.practice;

import org.flywaydb.core.Flyway;
import org.juric.sharding.config.LogicalRepository;
import org.juric.sharding.config.PhysicalDatabase;
import org.juric.sharding.config.RepositoryConfig;
import org.juric.sharding.datasource.ShardingDataSourceManager;
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
    RepositoryConfig<PhysicalDatabase> repositoryConfig;
    ShardingDataSourceManager dataSourceManager;

    @Resource(name = "databaseConfig")
    public void setRepositoryConfig(RepositoryConfig<PhysicalDatabase> repositoryConfig) {
        this.repositoryConfig = repositoryConfig;
    }

    @Resource(name = "dataSourceManager")
    public void setDataSourceManager(ShardingDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    public void run(OperationMode operationMode) {
        try {
            for (LogicalRepository<PhysicalDatabase> logicalDatabase : repositoryConfig.getAllLogicalDatabase()) {
                for(int physicalShardId : logicalDatabase.getPhysicalRepositoryIds()) {
                    PhysicalDatabase physicalDatabase = logicalDatabase.getPhysicalShard(physicalShardId);
                    createSchemaIfNotExist(physicalDatabase);
                    DataSource dataSource = dataSourceManager.get(logicalDatabase.getName(), physicalShardId);
                    run(operationMode, logicalDatabase.getName(), dataSource);
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

    private void createSchemaIfNotExist(PhysicalDatabase physicalDatabase) throws SQLException {
        if (!connectionMap.containsKey(physicalDatabase.getHost())) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setUrl("jdbc:mysql://" + physicalDatabase.getHost());
            dataSource.setPassword("#Bugsfor$");
            connectionMap.put(physicalDatabase.getHost(), dataSource.getConnection());
        }

        Statement statement = connectionMap.get(physicalDatabase.getHost()).createStatement();
        statement.execute(String.format(CREATE_SCHEMA_TEMPLATE, physicalDatabase.getSchema() , COLLATE));

    }

    private void run(OperationMode operationMode, String logicalDbName, DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setLocations(new String[]{SCRIPT_ROOT + "/" + logicalDbName});
        flyway.setDataSource(dataSource);
        flyway.setValidateOnMigrate(true);
        flyway.setEncoding("utf-8");
        switch (operationMode) {
            case REPAIR:
                flyway.repair();
                break;
            default:
                flyway.migrate();
        }
    }
}
