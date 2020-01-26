package com.payments.service.controller;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.http.RequestPipeline;
import com.payments.service.http.ResponseType;
import com.payments.service.model.Account;
import com.payments.service.model.Response;
import com.payments.service.model.exceptions.AccountException;
import com.payments.service.service.AccountService;
import com.payments.service.service.json.GenericJsonSerializer;

import javax.inject.Inject;

import java.util.Optional;

import static com.payments.service.controller.AccountController.AccountPath.ACCOUNT_BY_ID;
import static spark.Spark.*;

public class AccountController {
    public interface AccountPath {
        String ACCOUNT = "/account";
        String ACCOUNT_BY_ID = "/account/:id";
    }

    @Inject
    public AccountController(AccountService service) {
        post(AccountPath.ACCOUNT, (req, res) -> {
                    res.status(HttpStatusCode.CREATED);
                    return RequestPipeline.<Account, Account>from(req).extractObject(Account.class).process(service::create);
                },
                GenericJsonSerializer::toJson);

        get(ACCOUNT_BY_ID,
                (req, res) -> RequestPipeline.<Integer, Account>from(req).extract("id").parse(Integer::parseInt).process(service::find),
                GenericJsonSerializer::toJson);

        delete(ACCOUNT_BY_ID,
                (req, res) -> RequestPipeline.<Integer, Integer>from(req).extract("id").parse(Integer::parseInt).process(service::delete),
                GenericJsonSerializer::toJson);

        exception(AccountException.class, (e, request, response) -> {
            response.status(Optional.ofNullable(e.getHttpStatusCode()).orElse(HttpStatusCode.BAD_REQUEST));
            response.type(ResponseType.APPLICATION_JSON);
            response.body(GenericJsonSerializer.toJson(Response.of(e.getMessage())));
        });
    }
}
