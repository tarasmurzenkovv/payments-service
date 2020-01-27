package com.payments.service;

import com.payments.service.model.Customer;
import com.squareup.okhttp.*;
import lombok.SneakyThrows;
import org.junit.*;

import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;
import static com.payments.service.http.HttpStatusCode.BAD_REQUEST;
import static com.payments.service.http.HttpStatusCode.CREATED;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static com.payments.service.service.json.GenericJsonSerializer.toJson;
import static com.squareup.okhttp.RequestBody.create;
import static org.junit.Assert.assertEquals;

public class CustomerControllerIT extends BaseIT{
    @Test
    @SneakyThrows
    public void shouldCreateCustomer() {
        var body = create(JSON, toJson(new Customer(null, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = of(response.body().string(), Customer.class);
        assertEquals(response.code(), CREATED);
        Assert.assertNotNull(createdCustomer);
        Assert.assertNotNull(createdCustomer.getId());
        assertEquals(createdCustomer.getName(), "name");
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateCustomerWithEmptyName() {
        var body = create(JSON, toJson(new Customer(null, "")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        assertEquals(response.code(), BAD_REQUEST);
    }

    @Test
    @SneakyThrows
    public void shouldGetCustomerById() {
        var body = create(JSON, toJson(new Customer(1, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = of(response.body().string(), Customer.class);
        var customerId = createdCustomer.getId();
        var requestToCreateNewCustomer = new Request.Builder()
                .url(TEST_HOST + CUSTOMER_URI + "/" + customerId)
                .build();
        var foundCustomerResponse = okHttpClient.newCall(requestToCreateNewCustomer).execute();
        Customer foundCustomer = of(foundCustomerResponse.body().string(), Customer.class);
        Assert.assertNotNull(foundCustomer);
        assertEquals(foundCustomer.getId(), customerId);
        assertEquals(foundCustomer.getName(), "name");
    }

    @Test
    @SneakyThrows
    public void shouldDeleteCustomerById() {
        var body = create(JSON, toJson(new Customer(1, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = of(response.body().string(), Customer.class);
        var customerId = createdCustomer.getId();
        var requestToDeleteCustomer = new Request.Builder()
                .url(TEST_HOST + CUSTOMER_URI + "/" + customerId)
                .delete()
                .build();
        var foundCustomerResponse = okHttpClient.newCall(requestToDeleteCustomer).execute();
        var deletedCustomerId = of(foundCustomerResponse.body().string(), Integer.class);
        assertEquals(deletedCustomerId, createdCustomer.getId());
    }
}
