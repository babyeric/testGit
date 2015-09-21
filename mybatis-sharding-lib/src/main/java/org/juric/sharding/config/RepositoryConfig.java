package org.juric.sharding.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryConfig<T extends PhysicalRepository> {
    private final Map<String, LogicalRepository<T>> logicalDatabaseMap = new HashMap<>();

    public LogicalRepository<T> getLogicalRepository(String logicalDbName) {
        return logicalDatabaseMap.get(logicalDbName);
    }

    public void addLogicalDatabase(LogicalRepository<T> logicalDatabase) {
        logicalDatabaseMap.put(logicalDatabase.getName(), logicalDatabase);
    }

    public Iterable<LogicalRepository<T>> getAllLogicalDatabase() {
        return logicalDatabaseMap.values();
    }
}
