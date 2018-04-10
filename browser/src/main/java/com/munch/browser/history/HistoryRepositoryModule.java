package com.munch.browser.history;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.munch.browser.utils.IOExecutor;
import com.munch.history.HistoryRepository;
import com.munch.history.data.local.HistoryDao;
import com.munch.history.data.local.HistoryDatabase;
import com.munch.history.data.local.LocalHistoryDataSource;
import com.munch.history.model.HistoryDataSource;
import com.munch.history.Local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class HistoryRepositoryModule {

    @Singleton
    @Provides
    static HistoryDatabase provideDb(Application context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                HistoryDatabase.class,
                "History.db")
                .build();
    }

    @Singleton
    @Provides
    static HistoryDao provideTasksDao(HistoryDatabase db) {
        return db.historyDao();
    }

    @Singleton
    @Provides
    @Local
    static HistoryDataSource provideTasksLocalDataSource(HistoryDao historyDao) {
        return new LocalHistoryDataSource(historyDao);
    }

    @Singleton
    @Provides
    static HistoryRepository provideHistoryRepository(IOExecutor ioExecutor,
                                                      LocalHistoryDataSource localHistoryDataSource) {
        return new HistoryRepositoryImpl(ioExecutor, localHistoryDataSource);
    }
}
