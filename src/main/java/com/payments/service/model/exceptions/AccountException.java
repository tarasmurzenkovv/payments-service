package com.payments.service.model.exceptions;

import lombok.Getter;

@Getter
public class AccountException extends RuntimeException {
    private Integer httpStatusCode;

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Integer httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
