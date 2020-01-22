package com.payments.service.controller.payments;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payments.service.service.json.MoneySerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String account;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
    private String currency;

    public static PaymentResponse from(PaymentRequest paymentRequest) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setAccount(paymentRequest.getAccountTo());
        paymentResponse.setAmount(paymentRequest.getAmount());
        paymentResponse.setCurrency(paymentRequest.getCurrency());
        return paymentResponse;
    }
}
