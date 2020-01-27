package com.payments.service.service;

import com.payments.service.dao.AccountDao;
import com.payments.service.model.Account;
import com.payments.service.model.exceptions.AccountException;
import com.payments.service.service.validation.ValidationService;

import javax.inject.Inject;

public class AccountService {
    private final AccountDao accountDao;
    private final ValidationService<Account> accountValidationService;

    @Inject
    public AccountService(AccountDao accountDao, ValidationService<Account> validationService) {
        this.accountDao = accountDao;
        this.accountValidationService = validationService;
    }

    public Account create(Account account) {
        accountValidationService.validate(account);
        var customerId = account.getCustomerId();
        var amount = account.getAmount();
        var accountId = accountDao.create(customerId, amount);
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
