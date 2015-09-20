package org.juric.sharding.transactional.spring;

import org.springframework.transaction.support.SmartTransactionObject;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/26/15
 * Time: 4:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShardingSmartTransactionObject implements SmartTransactionObject{
    private ShardingTransactionObject transactionObject;

    public void set(ShardingTransactionObject transactionObject) {
        this.transactionObject = transactionObject;
    }

    public ShardingTransactionObject get() {
        return transactionObject;
    }

    @Override
    public boolean isRollbackOnly() {
        return transactionObject.isRollbackOnly();
    }

    @Override
    public void flush() {
    }
}
