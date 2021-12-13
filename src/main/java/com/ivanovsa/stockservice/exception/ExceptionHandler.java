package com.ivanovsa.stockservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorDto> handleNotFound(Exception ex) {
        return new ResponseEntity<ErrorDto>(
                new ErrorDto(ex.getLocalizedMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
