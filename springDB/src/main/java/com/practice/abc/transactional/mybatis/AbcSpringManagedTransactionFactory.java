package com.practice.abc.transactional.mybatis;

import com.practice.abc.lazyPop.AbcDataSourceManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
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
public class AbcSpringManagedTransactionFactory extends SpringManagedTransactionFactory {
    private AbcDataSourceManager dataSourceManager;

    @Override
    public Transaction newTransaction(Connection conn) {
        throw new UnsupportedOperationException("New Spring transactions require a DataSource");
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        // get data source from thread local
        return new AbcSpringManagedTransaction(this);
    }

    public void setDataSourceManager(AbcDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    public AbcDataSourceManager getDataSourceManager() {
        return dataSourceManager;
    }

}
