package com.payments.service.dao;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PaymentDao_Factory implements Factory<PaymentDao> {
  private final Provider<ConnectionManager> connectionManagerProvider;

  public PaymentDao_Factory(Provider<ConnectionManager> connectionManagerProvider) {
    this.connectionManagerProvider = connectionManagerProvider;
  }

  @Override
  public PaymentDao get() {
    return provideInstance(connectionManagerProvider);
  }

  public static PaymentDao provideInstance(Provider<ConnectionManager> connectionManagerProvider) {
    return new PaymentDao(connectionManagerProvider.get());
  }

  public static PaymentDao_Factory create(Provider<ConnectionManager> connectionManagerProvider) {
    return new PaymentDao_Factory(connectionManagerProvider);
  }

  public static PaymentDao newPaymentDao(ConnectionManager connectionManager) {
    return new PaymentDao(connectionManager);
  }
}
