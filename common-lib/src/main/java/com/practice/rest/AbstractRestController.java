package com.practice.rest;

import com.practice.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/29/15
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRestController {

    @ExceptionHandler({ValidationException.class})
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
