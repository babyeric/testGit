package org.juric.sharding.transactional.spring;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/20/15
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingTransactionManager extends AbstractPlatformTransactionManager {
    private static final ThreadLocal<ShardingTransactionObject> currentTransactionObject
            = new ThreadLocal<ShardingTransactionObject>();

    public static ShardingTransactionObject getCurrentTransactionObject() {
        return currentTransactionObject.get();
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        ShardingSmartTransactionObject smartTransactionObject = new ShardingSmartTransactionObject();
        smartTransactionObject.set(getCurrentTransactionObject());
        return smartTransactionObject;
    }

    @Override
    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        ShardingSmartTransactionObject smartTransactionObject = (ShardingSmartTransactionObject) transaction;

        boolean result = smartTransactionObject.get() != null;
        return result;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        ShardingTransactionObject transactionObject = new ShardingTransactionObject(this);
        transactionObject.setTransactionDefinition(definition);

        ShardingSmartTransactionObject  smartTransactionObject = (ShardingSmartTransactionObject)transaction;
        smartTransactionObject.set(transactionObject);

        currentTransactionObject.set(transactionObject);
        return;
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        ShardingSmartTransactionObject smartObject = (ShardingSmartTransactionObject) status.getTransaction();
        smartObject.get().commit();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        ShardingSmartTransactionObject smartObject = (ShardingSmartTransactionObject) status.getTransaction();
        smartObject.get().rollback();
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        ShardingSmartTransactionObject smartObject = (ShardingSmartTransactionObject) status.getTransaction();
        smartObject.get().setRollbackOnly();
    }

    @Override
    protected Object doSuspend(Object transaction) throws TransactionException {
        ShardingTransactionObject transactionObject = getCurrentTransactionObject();
        currentTransactionObject.set(null);

        transactionObject.suspend();
        return transactionObject;
    }

    @Override
    protected void doResume(Object transaction, Object suspendedResources) throws TransactionException {
        ShardingTransactionObject transactionObject = (ShardingTransactionObject)suspendedResources;

        transactionObject.resume();

        if (getCurrentTransactionObject() != null) {
            throw new IllegalStateException("currentTransaction != null");
        }
        currentTransactionObject.set(transactionObject);
    }

    @Override
    protected boolean useSavepointForNestedTransaction() {
        return false;
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        ShardingSmartTransactionObject smartObject = (ShardingSmartTransactionObject) transaction;
        ShardingTransactionObject transactionObject = smartObject.get();

        if (transactionObject != getCurrentTransactionObject()) {
            throw new IllegalStateException("transactionObject != currentTransactionObject");
        }

        transactionObject.cleanup();
        currentTransactionObject.set(null);
    }

}
