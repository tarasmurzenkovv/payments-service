package com.payments.service.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payments.service.service.json.MoneySerializer;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private int customerId;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;

    public static Account of(Integer accountId, int customerId, BigDecimal amount) {
        return new Account(accountId, customerId, amount);
    }
}
