package com.atradius.cafe.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class OverpaymentException extends RuntimeException{
    private final HttpStatus statusCode;

    public OverpaymentException(final HttpStatus statusCode, final String statusMessage) {
        super(statusMessage);
       this.statusCode = statusCode;
    }

    public OverpaymentException(final String statusMessage) {
        this(HttpStatus.UNPROCESSABLE_ENTITY, statusMessage);
    }


}
