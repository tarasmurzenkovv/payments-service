package com.payments.service.http;

import com.payments.service.service.json.GenericJsonSerializer;
import com.payments.service.utils.StringUtils;
import spark.Request;
import spark.Response;

import java.util.function.Consumer;
import java.util.function.Function;

public class RequestPipeline<T, U> {
    private final Request request;
    private String rawStringValue;
    private T parsedValue;

    private RequestPipeline(Request request) {
        this.request = request;
    }

    public static <T, U> RequestPipeline<T, U> from(Request request) {
        return new RequestPipeline<>(request);
    }

    public RequestPipeline<T, U> extractObject(Class<T> tClass) {
        parsedValue = GenericJsonSerializer.of(this.request, tClass);
        return this;
    }

    public RequestPipeline<T, U> extract(String parameterName) {
        rawStringValue = request.params(parameterName);
        if (StringUtils.isEmpty(rawStringValue)) {
            throw new RuntimeException(
                    String.format("Cannot find path parameter value from request for the given name '%s'", parameterName));
        }
        return this;
    }

    public RequestPipeline<T, U> parse(Function<String, T> parser) {
        parsedValue = parser.apply(rawStringValue);
        return this;
    }

    public  RequestPipeline<T, U> responseStatus(Response response, Consumer<Response> responseConsumer) {
        responseConsumer.accept(response);
        return this;
    }

    public U process(Function<T, U> processor) {
        return processor.apply(parsedValue);
    }
}
