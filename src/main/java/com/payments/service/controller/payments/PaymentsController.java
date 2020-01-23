package com.payments.service.controller.payments;

import com.payments.service.model.Payment;
import com.payments.service.service.json.GenericJsonSerializer;
import com.payments.service.service.payments.PaymentService;

import javax.inject.Inject;

import static com.payments.service.controller.payments.PaymentsPath.CREDIT_URI;
import static com.payments.service.service.json.GenericJsonSerializer.of;
import static spark.Spark.put;

public class PaymentsController {
    @Inject
    public PaymentsController(PaymentService paymentService) {
        put(CREDIT_URI, (req, res) -> paymentService.credit(of(req, Payment.class)), GenericJsonSerializer::toJson);
    }
}
