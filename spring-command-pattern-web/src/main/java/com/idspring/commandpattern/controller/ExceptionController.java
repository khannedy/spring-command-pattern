package com.idspring.commandpattern.controller;

import com.idspring.commandpattern.model.controller.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Eko Kurniawan Khannedy
 * @since 01/07/17
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handleThrowable(Throwable throwable) {
        return Response.status(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
    }

}
