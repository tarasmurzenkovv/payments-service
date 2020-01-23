package com.payments.service.dao;

import javax.inject.Inject;

public class PaymentDao {

    private final DbDao dbDao;

    @Inject
    public PaymentDao(DbDao dbDao) {
        this.dbDao = dbDao;
    }

    public void success(){
        System.out.println(dbDao != null);
    }
}
