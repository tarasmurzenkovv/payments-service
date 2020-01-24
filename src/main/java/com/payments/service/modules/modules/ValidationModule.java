package com.payments.service.modules.modules;

import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.payments.service.service.customer.validation.AccountValidationService;
import com.payments.service.service.customer.validation.CustomerValidationService;
import com.payments.service.service.customer.validation.ValidationService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ValidationModule {
    @Provides
    @Singleton
    public ValidationService<Customer> customerValidationService() {
        return new CustomerValidationService();
    }

    @Provides
    @Singleton
    public ValidationService<Account> accountValidationService() {
        return new AccountValidationService();
    }
}
