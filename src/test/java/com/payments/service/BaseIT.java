package com.payments.service;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import spark.Spark;

public class BaseIT {
    protected static final MediaType JSON = MediaType.parse("application/json");
    protected static final String TEST_HOST = "http://localhost:4567";
    protected static OkHttpClient okHttpClient;

    @BeforeClass
    public static void init() {
        Main.main(null);
        Spark.awaitInitialization();
        okHttpClient = new OkHttpClient();
    }

    @AfterClass
    public static void tearDown() {
        Spark.awaitStop();
        Spark.stop();
    }
}
