package org.juric.sharding.session;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.juric.sharding.mapper.ShardingMapperContext;
import org.mybatis.spring.SqlSessionHolder;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static org.springframework.transaction.support.TransactionSynchronizationManager.*;
import static org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive;
import static org.springframework.transaction.support.TransactionSynchronizationManager.unbindResource;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/9/15
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ShardingSqlSessionUtils {

    private static final Log logger = LogFactory.getLog(ShardingSqlSessionUtils.class);

    private ShardingSqlSessionUtils() {
        // do nothing
    }

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory) {
        ExecutorType executorType = sessionFactory.getConfiguration().getDefaultExecutorType();
        return getSqlSession(sessionFactory, executorType, null);
    }

    public static SqlSession getSqlSession(SqlSessionFactory sessionFactory, ExecutorType executorType,
                                           PersistenceExceptionTranslator exceptionTranslator) {
        notNull(sessionFactory, "No SqlSessionFactory specified");
        notNull(executorType, "No ExecutorType specified");

        Object sqlSessionholderKey = getSqlSessionHolderKey();
        SqlSessionHolder holder = (SqlSessionHolder) getResource(sqlSessionholderKey);

        if (holder != null && holder.isSynchronizedWithTransaction()) {
            if (holder.getExecutorType() != executorType) {
                throw new TransientDataAccessResourceException(
                        "Cannot change the ExecutorType when there is an existing transaction");
            }

            holder.requested();

            if (logger.isDebugEnabled()) {
                logger.debug("Fetched SqlSession [" + holder.getSqlSession() + "] from current transaction");
            }

            return holder.getSqlSession();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Creating a new SqlSession");
        }

        SqlSession session = sessionFactory.openSession(executorType);

        // replace L1 cache with cloned perpetual cache
        //replaceCache(session);

        // Register session holder if synchronization is active (i.e. a Spring TX is active)
        //
        // Note: The DataSource used by the Environment should be synchronized with the
        // transaction either through DataSourceTxMgr or another tx synchronization.
        // Further assume that if an exception is thrown, whatever started the transaction will
        // handle closing / rolling back the Connection associated with the SqlSession.
        if (isSynchronizationActive()) {
            Environment environment = sessionFactory.getConfiguration().getEnvironment();

            if (environment.getTransactionFactory() instanceof SpringManagedTransactionFactory) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Registering transaction synchronization for SqlSession [" + session + "]");
                }

                holder = new SqlSessionHolder(session, executorType, exceptionTranslator);
                bindResource(sqlSessionholderKey, holder);
                registerSynchronization(new SqlSessionSynchronization(holder, sqlSessionholderKey));
                holder.setSynchronizedWithTransaction(true);
                holder.requested();
            } else {
                if (getResource(environment.getDataSource()) == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("SqlSession [" + session
                                + "] was not registered for synchronization because DataSource is not transactional");
                    }
                } else {
                    throw new TransientDataAccessResourceException(
                            "SqlSessionFactory must be using a SpringManagedTransactionFactory in order to use Spring transaction synchronization");
                }
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("SqlSession [" + session
                        + "] was not registered for synchronization because synchronization is not active");
            }
        }

        return session;
    }

    public static void closeSqlSession(SqlSession session) {
        notNull(session, "No SqlSession specified");

        SqlSessionHolder holder = (SqlSessionHolder) getResource(getSqlSessionHolderKey());
        if ((holder != null) && (holder.getSqlSession() == session)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Releasing transactional SqlSession [" + session + "]");
            }
            holder.released();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Closing non transactional SqlSession [" + session + "]");
            }
            session.close();
        }
    }

    public static boolean isSqlSessionTransactional(SqlSession session) {
        notNull(session, "No SqlSession specified");
        SqlSessionHolder holder = (SqlSessionHolder) getResource(getSqlSessionHolderKey());

        return (holder != null) && (holder.getSqlSession() == session);
    }

    private static Object getSqlSessionHolderKey() {
        return ShardingMapperContext.getMapperContext().getSqlSessionHolderKey();
    }
    /*
    private static void replaceCache(SqlSession session) {
        Executor executor = Helper.getFieldValue(session, "executor");

        if (executor instanceof Proxy) {
            InvocationHandler handler = Proxy.getInvocationHandler(executor);
            if (handler instanceof Plugin) {
                executor = Helper.getFieldValue(handler, "target");
                if (logger.isDebugEnabled()) {
                    logger.debug("SqlSession [" + session + "] executor is unwrapped from Plugin proxy.");
                }
            }
        }

        if (executor instanceof CachingExecutor) {
            executor = Helper.getFieldValue(executor, "delegate");
            if (logger.isDebugEnabled()) {
                logger.debug("SqlSession [" + session + "] executor is unwrapped from CachingExecutor.");
            }
        }

        if (executor instanceof BaseExecutor) {
            Helper.setFieldValue(executor, "localCache", new ClonedPerpetualCache("LocalCache"));
            Helper.setFieldValue(executor, "localOutputParameterCache", new ClonedPerpetualCache(
                    "LocalOutputParameterCache"));

            if (logger.isDebugEnabled()) {
                logger.debug("SqlSession [" + session + "] local cache is replaced with cloned cache.");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("SqlSession [" + session + "] local cache can't be replaced with cloned cache.");
            }
        }
    }*/

    private static final class SqlSessionSynchronization extends TransactionSynchronizationAdapter {

        private final SqlSessionHolder holder;

        private final Object sqlSessionHolderKey;

        public SqlSessionSynchronization(SqlSessionHolder holder, Object sqlSessionHolderKey) {
            notNull(holder, "Parameter 'holder' must be not null");
            notNull(sqlSessionHolderKey, "Parameter 'sqlSessionHolderKey' must be not null");

            this.holder = holder;
            this.sqlSessionHolderKey = sqlSessionHolderKey;
        }

        @Override
        public int getOrder() {
            // order right before any Connection synchronization
            return DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER - 1;
        }

        @Override
        public void suspend() {
            unbindResource(sqlSessionHolderKey);
        }

        @Override
        public void resume() {
            bindResource(sqlSessionHolderKey, this.holder);
        }

        @Override
        public void beforeCommit(boolean readOnly) {
            // Flush BATCH statements so they are actually executed before the connection is committed.
            // If there is no tx active data will be rolled back so there is no need to flush batches
            if (this.holder.getExecutorType() == ExecutorType.BATCH && isActualTransactionActive()) {
                try {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Transaction synchronization flushing SqlSession [" + this.holder.getSqlSession()
                                + "]");
                    }
                    this.holder.getSqlSession().flushStatements();
                } catch (PersistenceException p) {
                    if (this.holder.getPersistenceExceptionTranslator() != null) {
                        DataAccessException translated = this.holder.getPersistenceExceptionTranslator()
                                .translateExceptionIfPossible(p);
                        if (translated != null) {
                            throw translated;
                        }
                    }
                    throw p;
                }
            }
        }

        @Override
        public void afterCompletion(int status) {
            // Unbind the SqlSession from tx synchronization
            // Note, commit/rollback is needed to ensure 2nd level cache is properly updated
            // SpringTransaction will no-op the connection commit/rollback
            try {
                // Do not call commit unless there is really a transaction;
                // no need to commit if just tx synchronization is active but no transaction was started
                if (isActualTransactionActive()) {
                    switch (status) {
                        case STATUS_COMMITTED:
                            if (logger.isDebugEnabled()) {
                                logger.debug("Transaction synchronization committing SqlSession ["
                                        + this.holder.getSqlSession() + "]");
                            }
                            holder.getSqlSession().commit();
                            break;
                        case STATUS_ROLLED_BACK:
                            if (logger.isDebugEnabled()) {
                                logger.debug("Transaction synchronization rolling back SqlSession ["
                                        + this.holder.getSqlSession() + "]");
                            }
                            holder.getSqlSession().rollback();
                            break;
                        default:
                            if (logger.isDebugEnabled()) {
                                logger.debug("Transaction synchronization ended with unknown status for SqlSession ["
                                        + this.holder.getSqlSession() + "]");
                            }
                    }
                }
            } finally {
                if (!holder.isOpen()) {
                    unbindResource(sqlSessionHolderKey);
                    try {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Transaction synchronization closing SqlSession ["
                                    + this.holder.getSqlSession() + "]");
                        }
                        this.holder.getSqlSession().close();
                    } finally {
                        this.holder.reset();
                    }
                }
            }
        }
    }
}