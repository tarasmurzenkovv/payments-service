package com.payments.service.modules;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.payments.service.model.Payment;
import com.payments.service.service.validation.AccountValidationService;
import com.payments.service.service.validation.CustomerValidationService;
import com.payments.service.service.validation.PaymentValidationService;
import com.payments.service.service.validation.ValidationService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = DbConnectionModule.class)
public class ValidationModule {
    @Singleton
    public ValidationService<Customer> customerValidationService() {
        return new CustomerValidationService();
    }

    @Provides
    @Singleton
    public ValidationService<Account> accountValidationService() {
        return new AccountValidationService();
    }

    @Provides
    @Singleton
    public ValidationService<Payment> paymentValidation(AccountDao accountDao) {
        return new PaymentValidationService(accountDao);
    }
}
