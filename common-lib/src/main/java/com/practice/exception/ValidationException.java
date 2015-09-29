package com.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/29/15
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Order")
public class ValidationException extends RuntimeException {

    public ValidationException(String message, Exception exception) {
        super(message, exception);
    }

    public ValidationException(String message) {
        super(message);
    }
}
