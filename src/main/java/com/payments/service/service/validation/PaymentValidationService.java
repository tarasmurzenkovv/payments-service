package com.payments.service.service.validation;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Payment;
import com.payments.service.service.validation.payments.chain.elements.AccountsExistsValidation;
import com.payments.service.service.validation.payments.chain.elements.NonNegativeAmountIsPresent;
import com.payments.service.service.validation.payments.chain.elements.SufficientFundsValidation;

import javax.inject.Inject;
import java.util.function.Consumer;

public class PaymentValidationService implements ValidationService<Payment> {
    private final Consumer<Payment> validationChain;

    @Inject
    public PaymentValidationService(AccountDao accountDao) {
        validationChain = new NonNegativeAmountIsPresent()
                .andThen(new AccountsExistsValidation(accountDao))
                .andThen(new SufficientFundsValidation(accountDao));
    }

    @Override
    public void validate(Payment entity) {
        validationChain.accept(entity);
    }
}
