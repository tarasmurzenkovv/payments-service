package com.payments.service.service.validation.payments.chain.elements;

import com.payments.service.model.Payment;
import com.payments.service.model.exceptions.PaymentException;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class NonNegativeAmountIsPresent implements Consumer<Payment> {
    @Override
    public void accept(Payment payment) {
        BigDecimal paymentAmount = payment.getAmount();
        if (BigDecimal.ZERO.compareTo(paymentAmount) > 0) {
            throw new PaymentException(String.format("Got negative amount during payment '%s'. It must be non-negative.", paymentAmount));
        }
    }
}
