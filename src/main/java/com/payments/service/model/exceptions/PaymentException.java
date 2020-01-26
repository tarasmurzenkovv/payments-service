package com.payments.service.model.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
