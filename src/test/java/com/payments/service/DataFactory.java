package com.payments.service;

import com.payments.service.model.Customer;
import com.payments.service.service.json.GenericJsonSerializer;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.SneakyThrows;

import static com.payments.service.BaseIT.*;
import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;

public class DataFactory {
    @SneakyThrows
    public static Customer createCustomer(String customerName) {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(null, customerName)));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        return GenericJsonSerializer.of(response.body().string(), Customer.class);
    }
}
