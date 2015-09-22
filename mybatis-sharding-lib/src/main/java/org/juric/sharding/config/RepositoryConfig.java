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
    private final Map<String, LogicalRepository<T>> logicalRepositoryMap = new HashMap<>();

    public LogicalRepository<T> getLogicalRepository(String logicalDbName) {
        return logicalRepositoryMap.get(logicalDbName);
    }

    public void addLogicalRepository(LogicalRepository<T> logicalDatabase) {
        logicalRepositoryMap.put(logicalDatabase.getName(), logicalDatabase);
    }

    public Iterable<LogicalRepository<T>> getAllLogicalDatabase() {
        return logicalRepositoryMap.values();
    }
}
