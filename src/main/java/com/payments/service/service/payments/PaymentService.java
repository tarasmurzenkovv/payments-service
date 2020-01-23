package com.payments.service.service.payments;

import com.payments.service.model.Payment;
import com.payments.service.dao.PaymentDao;

import javax.inject.Inject;

public class PaymentService {
    private final PaymentDao paymentDao;

    @Inject
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public Payment credit(Payment payment) {
        paymentDao.success();
        return Payment.from(payment);
    }
}
