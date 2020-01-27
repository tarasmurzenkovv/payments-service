package com.payments.service.modules;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.payments.service.model.Payment;
import com.payments.service.service.validation.AccountValidationService;
import com.payments.service.service.validation.CustomerValidationService;
import com.payments.service.service.validation.PaymentValidationService;
import com.payments.service.service.validation.ValidationService;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<ValidationService<Payment>>(){}).to(PaymentValidationService.class);
        bind(new TypeLiteral<ValidationService<Customer>>(){}).to(CustomerValidationService.class);
        bind(new TypeLiteral<ValidationService<Account>>(){}).to(AccountValidationService.class);
    }
}
