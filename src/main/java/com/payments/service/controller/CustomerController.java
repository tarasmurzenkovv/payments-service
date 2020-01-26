package com.payments.service.controller;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.http.RequestPipeline;
import com.payments.service.http.ResponseType;
import com.payments.service.model.Customer;
import com.payments.service.model.Response;
import com.payments.service.model.exceptions.CustomerException;
import com.payments.service.service.json.GenericJsonSerializer;
import com.payments.service.service.customer.CustomerService;

import javax.inject.Inject;

import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_BY_ID_URI;
import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;
import static spark.Spark.*;

public class CustomerController {
    public interface CustomerPath {
        String CUSTOMER_URI = "/customer";
        String CUSTOMER_BY_ID_URI = "/customer/:id";
    }

    @Inject
    public CustomerController(CustomerService customerService) {
        post(CUSTOMER_URI,
                (req, res) -> RequestPipeline.<Customer, Customer>from(req).extractObject(Customer.class).process(customerService::create),
                GenericJsonSerializer::toJson);

        get(CUSTOMER_BY_ID_URI,
                (req, res) -> RequestPipeline.<Integer, Customer>from(req).extract("id").parse(Integer::parseInt).process(customerService::find),
                GenericJsonSerializer::toJson);

        delete(CUSTOMER_BY_ID_URI,
                (req, res) -> RequestPipeline.<Integer, Integer>from(req).extract("id").parse(Integer::parseInt).process(customerService::delete),
                GenericJsonSerializer::toJson);

        exception(CustomerException.class, (e, request, response) -> {
            response.status(HttpStatusCode.BAD_REQUEST);
            response.type(ResponseType.APPLICATION_JSON);
            response.body(GenericJsonSerializer.toJson(Response.of(e.getMessage())));
        });
    }
}
