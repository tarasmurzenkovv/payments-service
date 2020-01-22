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
public class PaymentRequest {
    private String accountTo;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
    private String currency;
}
