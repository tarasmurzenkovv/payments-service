package com.payments.service.http;

import com.payments.service.utils.IntegerParser;
import spark.Request;

import java.util.Optional;
import java.util.function.Function;

public interface RequestUtils {
    static <T> T transform(Request request, String pathParameter, Function<Integer, T> transformer) {
        return Optional.ofNullable(request.params(pathParameter))
                .filter(IntegerParser::isInt)
                .map(Integer::parseInt)
                .map(transformer)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot parse int from 'id' value '%s'", request.params(pathParameter))));
    }
}
