package com.payments.service.service;

import com.payments.service.model.Payment;
import com.payments.service.dao.PaymentDao;
import com.payments.service.service.validation.PaymentValidationService;
import com.payments.service.service.validation.ValidationService;

import javax.inject.Inject;

public class PaymentService {
    private final PaymentDao paymentDao;
    private final ValidationService<Payment> paymentValidationService;

    @Inject
    public PaymentService(PaymentDao paymentDao, ValidationService<Payment> paymentValidationService) {
        this.paymentDao = paymentDao;
        this.paymentValidationService = paymentValidationService;
    }

    public synchronized Payment transfer(Payment payment) {
        paymentValidationService.validate(payment);
        paymentDao.transfer(payment.getAccountFrom(), payment.getAccountTo(), payment.getAmount());
        return Payment.from(payment);
    }
}
