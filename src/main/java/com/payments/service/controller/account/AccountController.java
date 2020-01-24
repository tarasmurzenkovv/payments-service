package com.payments.service.controller.account;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.http.RequestPipeline;
import com.payments.service.http.ResponseType;
import com.payments.service.model.Account;
import com.payments.service.model.Response;
import com.payments.service.model.exceptions.AccountException;
import com.payments.service.service.customer.AccountService;
import com.payments.service.service.json.GenericJsonSerializer;

import javax.inject.Inject;

import static com.payments.service.controller.customer.CustomerPath.CUSTOMER_BY_ID_URI;
import static com.payments.service.controller.customer.CustomerPath.CUSTOMER_URI;
import static spark.Spark.*;

public class AccountController {

    @Inject
    public AccountController(AccountService service) {
        post(CUSTOMER_URI,
                (req, res) -> RequestPipeline.<Account, Account>from(req).extractObject(Account.class).process(service::create),
                GenericJsonSerializer::toJson);

        get(CUSTOMER_BY_ID_URI,
                (req, res) -> RequestPipeline.<Integer, Account>from(req).extract("id").parse(Integer::parseInt).process(service::find),
                GenericJsonSerializer::toJson);

        delete(CUSTOMER_BY_ID_URI,
                (req, res) -> RequestPipeline.<Integer, Integer>from(req).extract("id").parse(Integer::parseInt).process(service::delete),
                GenericJsonSerializer::toJson);

        exception(AccountException.class, (e, request, response) -> {
            response.status(HttpStatusCode.BAD_REQUEST);
            response.type(ResponseType.APPLICATION_JSON);
            response.body(GenericJsonSerializer.toJson(Response.of(e.getMessage())));
        });
    }
}
