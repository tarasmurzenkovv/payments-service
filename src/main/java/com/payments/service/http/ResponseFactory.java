package com.payments.service.http;

import com.payments.service.service.json.GenericJsonSerializer;
import spark.Response;

public class ResponseFactory {
    public static  <T> String toCreatedResponse(Response response, T body) {
        String bodyAsJson = GenericJsonSerializer.toJson(body);
        response.status(HttpStatusCode.CREATED);
        response.type(ResponseType.APPLICATION_JSON);
        return bodyAsJson;
    }
}
