package com.payments.service.model.exceptions;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {
    private Integer httpStatusCode;
    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
