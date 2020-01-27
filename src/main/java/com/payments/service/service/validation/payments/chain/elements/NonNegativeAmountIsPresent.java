package com.payments.service.service.validation.payments.chain.elements;

import com.payments.service.model.Payment;
import com.payments.service.model.exceptions.PaymentException;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

public class NonNegativeAmountIsPresent implements Consumer<Payment> {
    @Override
    public void accept(Payment payment) {
        var paymentAmount = payment.getAmount();
        if (ZERO.compareTo(paymentAmount) > 0) {
            throw new PaymentException(format("Got negative amount during payment '%s'. It must be non-negative.", paymentAmount));
        }
    }
}
