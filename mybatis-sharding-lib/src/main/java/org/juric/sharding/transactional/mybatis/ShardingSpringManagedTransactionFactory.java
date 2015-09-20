package org.juric.sharding.transactional.mybatis;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.juric.sharding.datasource.ShardingDataSourceManager;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingSpringManagedTransactionFactory extends SpringManagedTransactionFactory {
    private ShardingDataSourceManager dataSourceManager;

    @Override
    public Transaction newTransaction(Connection conn) {
        throw new UnsupportedOperationException("New Spring transactions require a DataSource");
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        // get data source from thread local
        return new ShardingSpringManagedTransaction(this);
    }

    public void setDataSourceManager(ShardingDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    public ShardingDataSourceManager getDataSourceManager() {
        return dataSourceManager;
    }

}
