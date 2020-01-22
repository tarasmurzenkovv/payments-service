package com.payments.service.service.payments;

import com.payments.service.controller.payments.PaymentRequest;
import com.payments.service.controller.payments.PaymentResponse;
import com.payments.service.dao.PaymentDao;

import javax.inject.Inject;

public class PaymentService {
    private final PaymentDao paymentDao;

    @Inject
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public PaymentResponse credit(PaymentRequest paymentRequest) {
        paymentDao.success();
        return PaymentResponse.from(paymentRequest);
    }
}
