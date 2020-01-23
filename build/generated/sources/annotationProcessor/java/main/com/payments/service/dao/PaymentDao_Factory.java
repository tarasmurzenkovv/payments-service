package com.payments.service.dao;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PaymentDao_Factory implements Factory<PaymentDao> {
  private final Provider<DbDao> dbDaoProvider;

  public PaymentDao_Factory(Provider<DbDao> dbDaoProvider) {
    this.dbDaoProvider = dbDaoProvider;
  }

  @Override
  public PaymentDao get() {
    return provideInstance(dbDaoProvider);
  }

  public static PaymentDao provideInstance(Provider<DbDao> dbDaoProvider) {
    return new PaymentDao(dbDaoProvider.get());
  }

  public static PaymentDao_Factory create(Provider<DbDao> dbDaoProvider) {
    return new PaymentDao_Factory(dbDaoProvider);
  }

  public static PaymentDao newPaymentDao(DbDao dbDao) {
    return new PaymentDao(dbDao);
  }
}
