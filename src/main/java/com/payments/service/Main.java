package com.payments.service;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.http.ResponseType;
import com.payments.service.model.Response;
import com.payments.service.modules.components.DaggerCustomerComponents;
import com.payments.service.modules.components.DaggerPaymentComponents;
import com.payments.service.service.json.GenericJsonSerializer;
import lombok.extern.slf4j.Slf4j;
import spark.Spark;

@Slf4j
public class Main {
    public static void main(String[] args) {
        DaggerCustomerComponents.create().buildCustomerController();
        DaggerPaymentComponents.create().buildPaymentController();
        Spark.after((req, res) -> res.type(ResponseType.APPLICATION_JSON));
        Spark.exception(RuntimeException.class, (e, req, res) -> {
            res.status(HttpStatusCode.BAD_REQUEST);
            res.type(ResponseType.APPLICATION_JSON);
            res.body(GenericJsonSerializer.toJson(Response.of(e.getMessage())));
        });
    }
}
