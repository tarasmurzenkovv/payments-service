package com.payments.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    private static final String EMPTY_STRING = "";

    public static boolean isEmpty(String string) {
        return string == null || EMPTY_STRING.equals(string);
    }
}
