package com.payments.service.controller.payments;

public interface PaymentsPath {
    String CREDIT_URI = "/account/:accountId/credit";
    String DEBIT_URI = "/account/:accountId/debit";
}
