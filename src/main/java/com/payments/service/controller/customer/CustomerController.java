package com.payments.service.controller.customer;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.http.ResponseType;
import com.payments.service.model.Customer;
import com.payments.service.model.Response;
import com.payments.service.model.exceptions.CustomerException;
import com.payments.service.service.json.GenericJsonSerializer;
import com.payments.service.service.customer.CustomerService;

import javax.inject.Inject;

import static com.payments.service.controller.customer.CustomerPath.CUSTOMER_BY_ID_URI;
import static com.payments.service.controller.customer.CustomerPath.CUSTOMER_URI;
import static com.payments.service.http.RequestUtils.transform;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static spark.Spark.*;

public class CustomerController {
    @Inject
    public CustomerController(CustomerService customerService) {
        post(CUSTOMER_URI, (req, res) -> customerService.create(of(req, Customer.class)), GenericJsonSerializer::toJson);
        get(CUSTOMER_BY_ID_URI, (req, res) -> transform(req, "id", customerService::find), GenericJsonSerializer::toJson);
        delete(CUSTOMER_BY_ID_URI, (req, res) -> transform(req, "id", customerService::delete), GenericJsonSerializer::toJson);

        exception(CustomerException.class, (e, request, response) -> {
            response.status(HttpStatusCode.BAD_REQUEST);
            response.type(ResponseType.APPLICATION_JSON);
            response.body(GenericJsonSerializer.toJson(Response.of(e.getMessage())));
        });
    }
}
