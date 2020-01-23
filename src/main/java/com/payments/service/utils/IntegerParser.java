package com.payments.service.utils;

public class IntegerParser {
    public static boolean isInt(String string) {
        return string.matches("-?\\d+");
    }
}
