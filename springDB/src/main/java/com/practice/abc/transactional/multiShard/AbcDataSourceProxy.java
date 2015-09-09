package com.practice.abc.transactional.multiShard;

import com.practice.abc.transactional.spring.AbcTransactionManager;
import com.practice.abc.transactional.spring.AbcTransactionObject;
import org.springframework.core.InfrastructureProxy;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/1/15
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbcDataSourceProxy extends DelegatingDataSource implements InfrastructureProxy{
    public AbcDataSourceProxy(DataSource target) {
        super(target);
    }

    @Override
    public Object getWrappedObject() {
        DataSource dataSource = getTargetDataSource();

        AbcTransactionObject txObject = AbcTransactionManager.getCurrentTransactionObject();
        if (txObject == null) {
            return dataSource;
        }

        ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        if (conHolder != null) {
            return dataSource;
        }

        Connection con = null;

        try {
            conHolder = new ConnectionHolder(dataSource.getConnection(), true);
            conHolder.setSynchronizedWithTransaction(true);
            TransactionDefinition definition = txObject.getTransactionDefinition();

            con = conHolder.getConnection();
            DataSourceUtils.prepareConnectionForTransaction(con, definition);

            if (!definition.isReadOnly() && con.isReadOnly()) {
                con.setReadOnly(false);
            }

            if (con.getAutoCommit()) {
                con.setAutoCommit(false);
            }

            if (definition.getTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
                conHolder.setTimeoutInSeconds(definition.getTimeout());
            } else if (txObject.getTransactionManager().getDefaultTimeout() != TransactionDefinition.TIMEOUT_DEFAULT) {
                conHolder.setTimeoutInSeconds(txObject.getTransactionManager().getDefaultTimeout());
            }

            TransactionSynchronizationManager.bindResource(dataSource, conHolder);

            txObject.enlistDataSource(dataSource, conHolder);
            return dataSource;

        } catch (SQLException e) {
            DataSourceUtils.releaseConnection(con, null);

            throw new CannotCreateTransactionException("Could not open JDBC Connection for transaction", e);
        }
    }
}
