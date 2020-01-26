package com.payments.service.controller;

import com.payments.service.http.RequestPipeline;
import com.payments.service.model.Payment;
import com.payments.service.model.Response;
import com.payments.service.service.json.GenericJsonSerializer;
import com.payments.service.service.PaymentService;

import javax.inject.Inject;

import static com.payments.service.controller.PaymentsController.PaymentsPath.PAYMENT;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static spark.Spark.put;

public class PaymentsController {
    public interface PaymentsPath {
        String PAYMENT = "/payment";
    }

    @Inject
    public PaymentsController(PaymentService paymentService) {
        put(PAYMENT,
                (req, res) ->
                        RequestPipeline.<Payment, Payment>from(req).extractObject(Payment.class).process(paymentService::transfer),
                GenericJsonSerializer::toJson);
    }
}
