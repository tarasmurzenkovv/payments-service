package com.payments.service.service.validation.payments.chain.elements;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Payment;
import com.payments.service.model.exceptions.PaymentException;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static java.lang.String.format;

public class SufficientFundsValidation implements Consumer<Payment> {
    private final AccountDao accountDao;

    public SufficientFundsValidation(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void accept(Payment payment) {
        var accountIdFrom = payment.getAccountFrom();
        var amount = payment.getAmount();
        accountDao.findById(accountIdFrom)
                .ifPresent(account -> {
                    if (account.getAmount().compareTo(amount) <= 0) {
                        throw new PaymentException(
                                format("Cannot credit amount '%s' from account '%s'. Insufficient funds.", amount, accountIdFrom));
                    }
                });

    }
}
