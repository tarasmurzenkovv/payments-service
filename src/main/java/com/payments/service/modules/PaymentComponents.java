package com.payments.service.modules;

import com.payments.service.controller.payments.PaymentsController;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DbConnectionModule.class)
public interface PaymentComponents {
    PaymentsController buildPaymentController();
}
