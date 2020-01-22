package com.payments.service.dao;

import javax.inject.Inject;

public class PaymentDao {

    private final DbConnection dbConnection;

    @Inject
    public PaymentDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void success(){
        System.out.println(dbConnection != null);
    }
}
