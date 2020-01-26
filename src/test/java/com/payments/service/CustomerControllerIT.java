package com.payments.service;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.model.Customer;
import com.payments.service.service.json.GenericJsonSerializer;
import com.squareup.okhttp.*;
import lombok.SneakyThrows;
import org.junit.*;

import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;

public class CustomerControllerIT extends BaseIT{
    @Test
    @SneakyThrows
    public void shouldCreateCustomer() {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(null, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = GenericJsonSerializer.of(response.body().string(), Customer.class);
        Assert.assertEquals(response.code(), HttpStatusCode.CREATED);
        Assert.assertNotNull(createdCustomer);
        Assert.assertNotNull(createdCustomer.getId());
        Assert.assertEquals(createdCustomer.getName(), "name");
    }

    @Test
    @SneakyThrows
    public void shouldNotCreateCustomerWithEmptyName() {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(null, "")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        Assert.assertEquals(response.code(), HttpStatusCode.BAD_REQUEST);
    }

    @Test
    @SneakyThrows
    public void shouldGetCustomerById() {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(1, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = GenericJsonSerializer.of(response.body().string(), Customer.class);
        var customerId = createdCustomer.getId();
        var requestToCreateNewCustomer = new Request.Builder()
                .url(TEST_HOST + CUSTOMER_URI + "/" + customerId)
                .build();
        var foundCustomerResponse = okHttpClient.newCall(requestToCreateNewCustomer).execute();
        Customer foundCustomer = GenericJsonSerializer.of(foundCustomerResponse.body().string(), Customer.class);
        Assert.assertNotNull(foundCustomer);
        Assert.assertEquals(foundCustomer.getId(), customerId);
        Assert.assertEquals(foundCustomer.getName(), "name");
    }

    @Test
    @SneakyThrows
    public void shouldDeleteCustomerById() {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(1, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = GenericJsonSerializer.of(response.body().string(), Customer.class);
        var customerId = createdCustomer.getId();
        var requestToDeleteCustomer = new Request.Builder()
                .url(TEST_HOST + CUSTOMER_URI + "/" + customerId)
                .delete()
                .build();
        var foundCustomerResponse = okHttpClient.newCall(requestToDeleteCustomer).execute();
        var deletedCustomerId = GenericJsonSerializer.of(foundCustomerResponse.body().string(), Integer.class);
        Assert.assertEquals(deletedCustomerId, createdCustomer.getId());
    }
}
