package com.payments.service.service.customer;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Account;
import com.payments.service.model.exceptions.AccountException;
import com.payments.service.service.customer.validation.ValidationService;

import javax.inject.Inject;
import java.math.BigDecimal;

public class AccountService {
    private final AccountDao accountDao;
    private final ValidationService<Account> validationService;

    @Inject
    public AccountService(AccountDao accountDao, ValidationService<Account> validationService) {
        this.accountDao = accountDao;
        this.validationService = validationService;
    }

    public Account create(Account account) {
        validationService.validate(account);
        int customerId = account.getCustomerId();
        BigDecimal amount = account.getAmount();
        int accountId = accountDao.create(customerId, amount);
        return Account.of(accountId, customerId, amount);
    }

    public Account find(int id) {
        return accountDao.findById(id)
                .orElseThrow(() -> new AccountException(String.format("Cannot find account with id '%s'", id)));
    }

    public int delete(int id) {
        return accountDao.deleteById(id);
    }
}
