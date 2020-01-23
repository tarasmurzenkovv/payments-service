package com.payments.service.model.exceptions;

public class CustomerException extends RuntimeException {
    public CustomerException(String message) {
        super(message);
    }
}
