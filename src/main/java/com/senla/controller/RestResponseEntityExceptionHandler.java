package com.senla.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //TODO: найти способ установить кодировку utf-8 для описания исключения на клиенте
    @ExceptionHandler(value = {
            NoSuchElementException.class,
            IllegalArgumentException.class,
            ServiceUnavailableException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest req) {
        String responseBody = ex.getMessage();
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }
}
