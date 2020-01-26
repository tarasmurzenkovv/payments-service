package com.payments.service.modules.components;

import com.payments.service.controller.PaymentsController;
import com.payments.service.modules.modules.DbConnectionModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DbConnectionModule.class)
public interface AccountComponents {
    PaymentsController buildPaymentController();
}
