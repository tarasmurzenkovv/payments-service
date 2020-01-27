package com.payments.service;

import com.payments.service.model.Account;
import com.payments.service.model.Customer;
import com.payments.service.model.Payment;
import com.payments.service.service.json.GenericJsonSerializer;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import lombok.SneakyThrows;
import org.junit.Test;

import java.math.BigDecimal;

import static com.payments.service.controller.AccountController.AccountPath.ACCOUNT;
import static com.payments.service.controller.CustomerController.CustomerPath.CUSTOMER_URI;
import static com.payments.service.controller.PaymentsController.PaymentsPath.PAYMENT;
import static com.payments.service.http.HttpStatusCode.CREATED;
import static com.payments.service.http.HttpStatusCode.OK;
import static com.payments.service.model.Account.of;
import static com.payments.service.model.Payment.of;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static com.payments.service.service.json.GenericJsonSerializer.toJson;
import static com.squareup.okhttp.RequestBody.create;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

public class PaymentControllerIT extends BaseIT {
    @Test
    @SneakyThrows
    public void shouldMakePayment() {
        var firstCustomerIdFrom = createCustomer("name1");
        var firstAccountId = createAccount(firstCustomerIdFrom, valueOf(100));
        var secondCustomerIdTo = createCustomer("name2");
        var secondAccountId = createAccount(secondCustomerIdTo, valueOf(500));
        var payment = of(firstAccountId, secondAccountId, valueOf(50));
        var createAccountRequestBody = create(JSON, toJson(payment));
        var paymentRequest = new Request.Builder().url(TEST_HOST + PAYMENT)
                .put(createAccountRequestBody)
                .build();

        var response = okHttpClient.newCall(paymentRequest).execute();
        assertEquals(response.code(), OK);
        assertEquals(getAccountBalance(firstAccountId).compareTo(BigDecimal.valueOf(50)), 0);
        assertEquals(getAccountBalance(secondAccountId).compareTo(BigDecimal.valueOf(550)), 0);
    }

    @SneakyThrows
    private BigDecimal getAccountBalance(int accountId) {
        var requestToFetchAccountAmount = new Request.Builder().url(TEST_HOST + ACCOUNT + "/" + accountId).build();
        var response = okHttpClient.newCall(requestToFetchAccountAmount).execute();
        return of(response.body().string(), Account.class).getAmount();
    }

    @SneakyThrows
    private int createAccount(int firstCustomerId1, BigDecimal amount) {
        var createAccountRequestBody = create(JSON, toJson(of(null, firstCustomerId1, amount)));
        var createAccountRequest = new Request.Builder().url(TEST_HOST + ACCOUNT)
                .post(createAccountRequestBody)
                .build();
        var response = okHttpClient.newCall(createAccountRequest).execute();
        assertEquals(response.code(), CREATED);
        return of(response.body().string(), Account.class).getId();
    }

    @SneakyThrows
    private int createCustomer(String name) {
        var body = RequestBody.create(JSON, GenericJsonSerializer.toJson(new Customer(null, name)));
        var createCustomerRequest = new Request.Builder().url(TEST_HOST + CUSTOMER_URI)
                .post(body)
                .build();
        var response = okHttpClient.newCall(createCustomerRequest).execute();
        assertEquals(response.code(), CREATED);
        return of(response.body().string(), Customer.class).getId();
    }
}
