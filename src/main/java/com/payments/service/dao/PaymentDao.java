package com.payments.service.dao;

import javax.inject.Inject;

public class PaymentDao {

    private final ConnectionManager connectionManager;

    @Inject
    public PaymentDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void success(){
        System.out.println(connectionManager != null);
    }
}
