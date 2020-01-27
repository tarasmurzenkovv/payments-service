package com.payments.service;

import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.squareup.okhttp.Request;
import lombok.SneakyThrows;
import org.junit.Test;

import static com.payments.service.controller.AccountController.AccountPath.ACCOUNT;
import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;
import static com.payments.service.http.HttpStatusCode.CREATED;
import static com.payments.service.model.Account.of;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static com.payments.service.service.json.GenericJsonSerializer.toJson;
import static com.squareup.okhttp.RequestBody.create;
import static java.lang.Integer.valueOf;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AccountControllerIT extends BaseIT {
    @Test
    @SneakyThrows
    public void shouldCreateAccount() {
        var body = create(JSON, toJson(new Customer(null, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var createdCustomerResponse = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = of(createdCustomerResponse.body().string(), Customer.class);
        var createdCustomerId = createdCustomer.getId();
        var expectedAccountAmount = valueOf(100.00);
        var createAccountRequestBody = create(JSON, toJson(of(null, createdCustomerId, expectedAccountAmount)));
        var createAccountRequest = new Request.Builder().url(TEST_HOST + ACCOUNT)
                .post(createAccountRequestBody)
                .build();
        var responseCreatedNewAccount = okHttpClient.newCall(createAccountRequest).execute();
        assertEquals(responseCreatedNewAccount.code(), CREATED);
        var account = of(responseCreatedNewAccount.body().string(), Account.class);
        assertNotNull(account);
        assertEquals(expectedAccountAmount.compareTo(account.getAmount()), 0);
        assertEquals(valueOf(account.getCustomerId()), createdCustomer.getId());
    }
}
