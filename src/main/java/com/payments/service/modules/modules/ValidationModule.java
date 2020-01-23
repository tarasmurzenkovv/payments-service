package com.payments.service.modules.modules;

import com.payments.service.service.customer.validation.CustomerValidationService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ValidationModule {
    @Provides
    @Singleton
    public CustomerValidationService customerValidationService() {
        return new CustomerValidationService();
    }
}
