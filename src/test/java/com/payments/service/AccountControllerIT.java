package com.payments.service;

import com.payments.service.http.HttpStatusCode;
import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.payments.service.service.json.GenericJsonSerializer;
import com.squareup.okhttp.Request;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import static com.payments.service.controller.AccountController.AccountPath.ACCOUNT;
import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;
import static com.payments.service.model.Account.of;
import static com.payments.service.service.json.GenericJsonSerializer.toJson;
import static com.squareup.okhttp.RequestBody.create;
import static java.math.BigDecimal.valueOf;

public class AccountControllerIT extends BaseIT {
    @Test
    @SneakyThrows
    public void shouldCreateAccount() {
        var body = create(JSON, toJson(new Customer(null, "name")));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var createdCustomerResponse = okHttpClient.newCall(createCustomerRequest).execute();
        var createdCustomer = GenericJsonSerializer.of(createdCustomerResponse.body().string(), Customer.class);
        var createdCustomerId = createdCustomer.getId();
        var expectedAccountAmount = valueOf(100.00);
        var createAccountRequestBody = create(JSON, toJson(of(null, createdCustomerId, expectedAccountAmount)));
        var createAccountRequest = new Request.Builder().url(TEST_HOST + ACCOUNT)
                .post(createAccountRequestBody)
                .build();
        var responseCreatedNewAccount = okHttpClient.newCall(createAccountRequest).execute();
        Assert.assertEquals(responseCreatedNewAccount.code(), HttpStatusCode.CREATED);
        Account account = GenericJsonSerializer.of(responseCreatedNewAccount.body().string(), Account.class);
        Assert.assertNotNull(account);
        Assert.assertEquals(expectedAccountAmount.compareTo(account.getAmount()), 0);
        Assert.assertEquals(Integer.valueOf(account.getCustomerId()), createdCustomer.getId());
    }
}
