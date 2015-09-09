package com.practice.abc.lazyPop.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/4/15
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataSourceConfig {
    private final Map<String, LogicalDatabase> logicalDatabaseMap = new HashMap<>();

    public LogicalDatabase getLogicalDatabase(String logicalDbName) {
        return logicalDatabaseMap.get(logicalDbName);
    }

    public void addLogicalDatabase(LogicalDatabase logicalDatabase) {
        logicalDatabaseMap.put(logicalDatabase.getName(), logicalDatabase);
    }

    public Iterable<LogicalDatabase> getAllLogicalDatabase() {
        return logicalDatabaseMap.values();
    }
}
