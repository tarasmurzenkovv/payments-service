package com.payments.service.service.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import spark.Request;

public class GenericJsonSerializer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static <T> T of(Request request, Class<T> instanceClass) {
        return OBJECT_MAPPER.readValue(request.body(), instanceClass);
    }

    @SneakyThrows
    public static <T> T of(String json, Class<T> instanceClass) {
        return OBJECT_MAPPER.readValue(json, instanceClass);
    }

    @SneakyThrows
    public static <T> String toJson(T instance) {
        return OBJECT_MAPPER.writeValueAsString(instance);
    }
}
