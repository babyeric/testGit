package com.juric.carbon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Eric on 10/18/2015.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Order")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
