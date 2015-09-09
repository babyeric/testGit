package com.practice.abc.transactional.spring;

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
public class AbcTransactionManager extends AbstractPlatformTransactionManager {
    private static final ThreadLocal<AbcTransactionObject> currentTransactionObject
            = new ThreadLocal<AbcTransactionObject>();

    public static AbcTransactionObject getCurrentTransactionObject() {
        return currentTransactionObject.get();
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        AbcSmartTransactionObject smartTransactionObject = new AbcSmartTransactionObject();
        smartTransactionObject.set(getCurrentTransactionObject());
        return smartTransactionObject;
    }

    @Override
    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        AbcSmartTransactionObject smartTransactionObject = (AbcSmartTransactionObject) transaction;

        boolean result = smartTransactionObject.get() != null;
        return result;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        AbcTransactionObject transactionObject = new AbcTransactionObject(this);
        transactionObject.setTransactionDefinition(definition);

        AbcSmartTransactionObject  smartTransactionObject = (AbcSmartTransactionObject)transaction;
        smartTransactionObject.set(transactionObject);

        currentTransactionObject.set(transactionObject);
        return;
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        AbcSmartTransactionObject smartObject = (AbcSmartTransactionObject) status.getTransaction();
        smartObject.get().commit();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        AbcSmartTransactionObject smartObject = (AbcSmartTransactionObject) status.getTransaction();
        smartObject.get().rollback();
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        AbcSmartTransactionObject smartObject = (AbcSmartTransactionObject) status.getTransaction();
        smartObject.get().setRollbackOnly();
    }

    @Override
    protected Object doSuspend(Object transaction) throws TransactionException {
        AbcTransactionObject transactionObject = getCurrentTransactionObject();
        currentTransactionObject.set(null);

        transactionObject.suspend();
        return transactionObject;
    }

    @Override
    protected void doResume(Object transaction, Object suspendedResources) throws TransactionException {
        AbcTransactionObject transactionObject = (AbcTransactionObject)suspendedResources;

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
        AbcSmartTransactionObject smartObject = (AbcSmartTransactionObject) transaction;
        AbcTransactionObject transactionObject = smartObject.get();

        if (transactionObject != getCurrentTransactionObject()) {
            throw new IllegalStateException("transactionObject != currentTransactionObject");
        }

        transactionObject.cleanup();
        currentTransactionObject.set(null);
    }

}
