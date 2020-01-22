package com.payments.service.controller.payments;

import com.payments.service.service.payments.PaymentService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PaymentsController_Factory implements Factory<PaymentsController> {
  private final Provider<PaymentService> paymentServiceProvider;

  public PaymentsController_Factory(Provider<PaymentService> paymentServiceProvider) {
    this.paymentServiceProvider = paymentServiceProvider;
  }

  @Override
  public PaymentsController get() {
    return provideInstance(paymentServiceProvider);
  }

  public static PaymentsController provideInstance(
      Provider<PaymentService> paymentServiceProvider) {
    return new PaymentsController(paymentServiceProvider.get());
  }

  public static PaymentsController_Factory create(Provider<PaymentService> paymentServiceProvider) {
    return new PaymentsController_Factory(paymentServiceProvider);
  }

  public static PaymentsController newPaymentsController(PaymentService paymentService) {
    return new PaymentsController(paymentService);
  }
}
