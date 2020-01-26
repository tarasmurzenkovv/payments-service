package com.payments.service.modules;

import com.payments.service.controller.AccountController;
import com.payments.service.controller.CustomerController;
import com.payments.service.controller.PaymentsController;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DbConnectionModule.class, ValidationModule.class})
public interface ControllersComponents {
    AccountController buildAccountController();

    CustomerController buildCustomerController();

    PaymentsController buildPaymentController();
}
