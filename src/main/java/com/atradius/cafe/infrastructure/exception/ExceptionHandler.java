package com.atradius.cafe.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = OverpaymentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage overpaymentException(final Exception exception, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exception.getMessage(),
                request.getDescription(false));
    }

}
