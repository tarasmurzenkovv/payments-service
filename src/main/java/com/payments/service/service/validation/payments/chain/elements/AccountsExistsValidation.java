package com.payments.service.service.validation.payments.chain.elements;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Pair;
import com.payments.service.model.Payment;
import com.payments.service.model.exceptions.PaymentException;

import javax.inject.Inject;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.payments.service.model.Pair.of;
import static java.lang.String.format;

public class AccountsExistsValidation implements Consumer<Payment> {
    private final AccountDao accountDao;

    public AccountsExistsValidation(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void accept(Payment payment) {
        Stream.of(payment.getAccountFrom(), payment.getAccountTo())
                .map(accountId -> of(accountId, accountDao.findById(accountId).isPresent()))
                .filter(pair -> !pair.getRight())
                .findAny()
                .map(Pair::getLeft)
                .ifPresent(accountId -> {
                    throw new PaymentException(format("Cannot locate registered account with id '%d'", accountId));
                });
    }
}
