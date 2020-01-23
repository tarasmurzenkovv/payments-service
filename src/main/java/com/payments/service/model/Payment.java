package com.payments.service.model;

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
public class Payment {
    private int accountTo;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;

    public static Payment from(Payment payment) {
        return new Payment(payment.accountTo, payment.amount);
    }
}
