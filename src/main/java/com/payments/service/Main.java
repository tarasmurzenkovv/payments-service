package com.payments.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.payments.service.controller.AccountController;
import com.payments.service.controller.CustomerController;
import com.payments.service.controller.PaymentsController;
import com.payments.service.modules.ApplicationModule;
import lombok.extern.slf4j.Slf4j;

import static com.payments.service.http.HttpStatusCode.BAD_REQUEST;
import static com.payments.service.http.ResponseType.APPLICATION_JSON;
import static com.payments.service.model.Response.of;
import static com.payments.service.service.json.GenericJsonSerializer.toJson;
import static spark.Spark.after;
import static spark.Spark.exception;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ApplicationModule());
        injector.getInstance(CustomerController.class);
        injector.getInstance(AccountController.class);
        injector.getInstance(PaymentsController.class);

        after((req, res) -> res.type(APPLICATION_JSON));
        exception(RuntimeException.class, (e, req, res) -> {
            res.status(BAD_REQUEST);
            res.type(APPLICATION_JSON);
            res.body(toJson(of(e.getMessage())));
        });
    }
}
