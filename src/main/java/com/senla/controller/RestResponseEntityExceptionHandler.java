package com.senla.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    /*
    value = {
        NoSuchElementException.class,
        IllegalArgumentException.class,
        ServiceUnavailableException.class,
        UsernameNotFoundException.class}
        */
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest req) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String responseBody = ex.getMessage();
        return handleExceptionInternal(ex, responseBody, httpHeaders, HttpStatus.BAD_REQUEST, req);
    }
}
