package org.juric.sharding.transactional.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.HeuristicCompletionException;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/26/15
 * Time: 4:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingTransactionObject {
    private final static Log LOG = LogFactory.getLog(ShardingTransactionObject.class);

    private ShardingTransactionManager transactionManager;
    private boolean isRollbackOnly = false;
    private TransactionDefinition transactionDefinition;
    private final Map<DataSource, ConnectionHolder> enlistedDataSources
            = new LinkedHashMap<DataSource, ConnectionHolder>();

    public void setRollbackOnly() {
        this.isRollbackOnly = true;
    }

    public boolean isRollbackOnly() {
        return isRollbackOnly;
    }

    public void setTransactionDefinition(TransactionDefinition transactionDefinition) {
        this.transactionDefinition = transactionDefinition;
    }

    public TransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public ShardingTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public ShardingTransactionObject(ShardingTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void enlistDataSource(DataSource dataSource, ConnectionHolder connHolder) {
        enlistedDataSources.put(dataSource, connHolder);

    }

    public void commit() {
        boolean commit = true;
        SQLException commitException = null;
        for (Map.Entry<DataSource, ConnectionHolder> entry : enlistedDataSources.entrySet()) {
            Connection con = entry.getValue().getConnection();

                if (commit) {
                    try {
                        con.commit();
                    } catch (SQLException e) {
                        commit = false;
                        commitException = e;
                        LOG.warn("commit failed", e);
                    }
                } else {
                    try {
                        con.rollback();
                    } catch (SQLException e) {
                        LOG.warn("rollback failed", e);
                    }
                }
        }

        if (commitException != null) {
            throw new HeuristicCompletionException(HeuristicCompletionException.STATE_MIXED, commitException);
        }
    }

    public void rollback() {
        SQLException rollbackException = null;
        for (Map.Entry<DataSource, ConnectionHolder> entry : enlistedDataSources.entrySet()) {
            Connection con = entry.getValue().getConnection();

            try {
                con.rollback();
            } catch (SQLException e) {
                if (rollbackException == null) {
                    rollbackException = e;
                }
                LOG.warn("rollback failed", e);
            }
        }

        if (rollbackException != null) {
            throw new HeuristicCompletionException(HeuristicCompletionException.STATE_MIXED, rollbackException);
        }
    }

    public void suspend() {
        for (DataSource dataSource : enlistedDataSources.keySet()) {
            TransactionSynchronizationManager.unbindResource(dataSource);
        }
    }

    public void resume() {
        for (Map.Entry<DataSource, ConnectionHolder> entry : enlistedDataSources.entrySet()) {
            TransactionSynchronizationManager.bindResource(entry.getKey(), entry.getValue());
        }
    }

    public void cleanup() {
        for (Map.Entry<DataSource, ConnectionHolder> entry : enlistedDataSources.entrySet()) {
            TransactionSynchronizationManager.unbindResource(entry.getKey());
            ConnectionHolder connHolder = entry.getValue();
            DataSourceUtils.releaseConnection(connHolder.getConnection(), null);

        }
    }
}
