package com.payments.service.service.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MoneySerializer extends JsonSerializer<BigDecimal> {
    @Override
    @SneakyThrows
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) {
        jgen.writeString(value.round(new MathContext(2, RoundingMode.HALF_UP)).toString());
    }
}
