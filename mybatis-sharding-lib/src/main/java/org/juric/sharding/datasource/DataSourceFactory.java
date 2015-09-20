package org.juric.sharding.datasource;

import javax.sql.DataSource;

/**
 * Created by Eric on 9/19/2015.
 */
public interface DataSourceFactory {
    DataSource createDataSource(String hostName, String schema);
}
