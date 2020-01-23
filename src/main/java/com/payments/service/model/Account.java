package com.payments.service.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.payments.service.service.json.MoneySerializer;

import java.math.BigDecimal;

public class Account {
    private Integer id;
    private Integer customerId;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
}
