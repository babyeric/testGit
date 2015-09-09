package com.practice.abc.transactional.mybatis;

import com.practice.abc.lazyPop.AbcMapperContext;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcSpringManagedTransaction implements Transaction {
    private AbcSpringManagedTransactionFactory factory;
    private Connection connection;
    private DataSource dataSourceRef;
    private boolean autoCommit;
    private boolean isConnectionTransactional;


    public AbcSpringManagedTransaction(AbcSpringManagedTransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            dataSourceRef = AbcMapperContext.getMapperContext().getDataSource();
            connection = DataSourceUtils.getConnection(dataSourceRef);
            autoCommit = connection.getAutoCommit();
            isConnectionTransactional =  DataSourceUtils.isConnectionTransactional(connection, dataSourceRef);
        }

        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !isConnectionTransactional && !autoCommit) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !isConnectionTransactional && !autoCommit) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        DataSourceUtils.releaseConnection(connection, dataSourceRef);
    }
}
