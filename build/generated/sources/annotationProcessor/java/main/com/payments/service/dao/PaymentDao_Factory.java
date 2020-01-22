package com.payments.service.dao;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PaymentDao_Factory implements Factory<PaymentDao> {
  private final Provider<DbConnection> dbConnectionProvider;

  public PaymentDao_Factory(Provider<DbConnection> dbConnectionProvider) {
    this.dbConnectionProvider = dbConnectionProvider;
  }

  @Override
  public PaymentDao get() {
    return provideInstance(dbConnectionProvider);
  }

  public static PaymentDao provideInstance(Provider<DbConnection> dbConnectionProvider) {
    return new PaymentDao(dbConnectionProvider.get());
  }

  public static PaymentDao_Factory create(Provider<DbConnection> dbConnectionProvider) {
    return new PaymentDao_Factory(dbConnectionProvider);
  }

  public static PaymentDao newPaymentDao(DbConnection dbConnection) {
    return new PaymentDao(dbConnection);
  }
}
