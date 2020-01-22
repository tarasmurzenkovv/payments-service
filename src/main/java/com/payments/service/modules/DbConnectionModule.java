package com.payments.service.modules;

import com.payments.service.dao.DbConnection;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DbConnectionModule {
    @Provides
    @Singleton
    public DbConnection paymentService() {
        return new DbConnection();
    }
}
