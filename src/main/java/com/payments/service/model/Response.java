package com.payments.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Response<T> {
    private T body;

    public static <T> Response<T> of(T body) {
        return new Response<>(body);
    }
}
