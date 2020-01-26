package com.payments.service.modules.components;

import com.payments.service.controller.CustomerController;
import com.payments.service.modules.modules.DbConnectionModule;
import com.payments.service.modules.modules.ValidationModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DbConnectionModule.class, ValidationModule.class})
public interface CustomerComponents {
    CustomerController buildCustomerController();
}
