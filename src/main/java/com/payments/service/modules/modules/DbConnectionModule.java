package com.payments.service.modules.modules;

import com.payments.service.dao.DbDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DbConnectionModule {
    @Provides
    @Singleton
    public DbDao dbDao() {
        return new DbDao();
    }
}
