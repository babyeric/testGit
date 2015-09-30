package com.practice.exception;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/30/15
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalException extends RuntimeException {
    public TransactionalException(Exception cause) {
        super(cause);
    }
}
