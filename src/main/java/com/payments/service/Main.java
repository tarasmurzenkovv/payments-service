package com.payments.service;

import com.payments.service.http.ResponseType;
import com.payments.service.modules.DaggerPaymentComponents;
import lombok.extern.slf4j.Slf4j;
import spark.Spark;

@Slf4j
public class Main {
    public static void main(String[] args) {
        DaggerPaymentComponents.create().buildPaymentController();
        Spark.after((req, res) -> res.type(ResponseType.APPLICATION_JSON));
    }
}
