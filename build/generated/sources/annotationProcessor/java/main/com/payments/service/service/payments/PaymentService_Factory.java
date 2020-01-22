package com.payments.service.service.payments;

import com.payments.service.dao.PaymentDao;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PaymentService_Factory implements Factory<PaymentService> {
  private final Provider<PaymentDao> paymentDaoProvider;

  public PaymentService_Factory(Provider<PaymentDao> paymentDaoProvider) {
    this.paymentDaoProvider = paymentDaoProvider;
  }

  @Override
  public PaymentService get() {
    return provideInstance(paymentDaoProvider);
  }

  public static PaymentService provideInstance(Provider<PaymentDao> paymentDaoProvider) {
    return new PaymentService(paymentDaoProvider.get());
  }

  public static PaymentService_Factory create(Provider<PaymentDao> paymentDaoProvider) {
    return new PaymentService_Factory(paymentDaoProvider);
  }

  public static PaymentService newPaymentService(PaymentDao paymentDao) {
    return new PaymentService(paymentDao);
  }
}
