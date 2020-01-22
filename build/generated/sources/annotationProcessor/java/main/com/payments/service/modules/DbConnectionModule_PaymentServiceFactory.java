package com.payments.service.modules;

import com.payments.service.dao.DbConnection;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DbConnectionModule_PaymentServiceFactory implements Factory<DbConnection> {
  private final DbConnectionModule module;

  public DbConnectionModule_PaymentServiceFactory(DbConnectionModule module) {
    this.module = module;
  }

  @Override
  public DbConnection get() {
    return provideInstance(module);
  }

  public static DbConnection provideInstance(DbConnectionModule module) {
    return proxyPaymentService(module);
  }

  public static DbConnectionModule_PaymentServiceFactory create(DbConnectionModule module) {
    return new DbConnectionModule_PaymentServiceFactory(module);
  }

  public static DbConnection proxyPaymentService(DbConnectionModule instance) {
    return Preconditions.checkNotNull(
        instance.paymentService(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
