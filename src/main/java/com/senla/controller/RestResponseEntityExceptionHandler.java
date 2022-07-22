package com.senla.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            IllegalAccessException.class,
            ServiceUnavailableException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest req) {
        String responseBody = ex.getMessage();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.CONFLICT, req);
    }
}
