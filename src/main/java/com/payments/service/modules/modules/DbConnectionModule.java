package com.payments.service.modules.modules;

import com.payments.service.dao.ConnectionManager;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DbConnectionModule {
    @Provides
    @Singleton
    public ConnectionManager dbDao() {
        return new ConnectionManager();
    }
}
