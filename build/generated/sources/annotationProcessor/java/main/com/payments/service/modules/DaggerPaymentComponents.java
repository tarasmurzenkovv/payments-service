package com.payments.service.modules;

import com.payments.service.controller.payments.PaymentsController;
import com.payments.service.dao.DbConnection;
import com.payments.service.dao.PaymentDao;
import com.payments.service.service.payments.PaymentService;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerPaymentComponents implements PaymentComponents {
  private Provider<DbConnection> paymentServiceProvider;

  private DaggerPaymentComponents(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static PaymentComponents create() {
    return new Builder().build();
  }

  private PaymentDao getPaymentDao() {
    return new PaymentDao(paymentServiceProvider.get());
  }

  private PaymentService getPaymentService() {
    return new PaymentService(getPaymentDao());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.paymentServiceProvider =
        DoubleCheck.provider(
            DbConnectionModule_PaymentServiceFactory.create(builder.dbConnectionModule));
  }

  @Override
  public PaymentsController buildPaymentController() {
    return new PaymentsController(getPaymentService());
  }

  public static final class Builder {
    private DbConnectionModule dbConnectionModule;

    private Builder() {}

    public PaymentComponents build() {
      if (dbConnectionModule == null) {
        this.dbConnectionModule = new DbConnectionModule();
      }
      return new DaggerPaymentComponents(this);
    }

    public Builder dbConnectionModule(DbConnectionModule dbConnectionModule) {
      this.dbConnectionModule = Preconditions.checkNotNull(dbConnectionModule);
      return this;
    }
  }
}
