package com.payments.service.service.validation.payments;

import com.payments.service.model.Payment;
import com.payments.service.service.validation.ValidationService;

import javax.inject.Inject;
import java.util.function.Consumer;

public class PaymentValidation implements ValidationService<Payment> {
    private final Consumer<Payment> validationChain;

    @Inject
    public PaymentValidation(Consumer<Payment> validationChain) {
        this.validationChain = validationChain;
    }

    @Override
    public void validate(Payment payment) {
        validationChain.accept(payment);
    }
}
