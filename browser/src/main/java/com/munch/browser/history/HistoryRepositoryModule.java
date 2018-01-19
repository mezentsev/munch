package com.munch.browser.history;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;

import com.munch.browser.utils.IOExecutor;
import com.munch.browser.utils.MainThreadExecutor;
import com.munch.history.data.local.HistoryDao;
import com.munch.history.data.local.HistoryDatabase;
import com.munch.history.data.local.LocalHistoryDataSource;
import com.munch.history.model.HistoryDataSource;
import com.munch.history.model.Local;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class HistoryRepositoryModule {

    @Singleton
    @Provides
    static HistoryDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), HistoryDatabase.class, "History.db")
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
    static HistoryDataSource provideTasksLocalDataSource(HistoryDao historyDao,
                                                         IOExecutor ioExecutor,
                                                         MainThreadExecutor mainThreadExecutor) {
        return new LocalHistoryDataSource(historyDao, ioExecutor, mainThreadExecutor);
    }

    /*@Singleton
    @Binds
    @Remote
    abstract HistoryDataSource provideTasksRemoteDataSource(FakeTasksRemoteDataSource dataSource);*/

    @Singleton
    @Provides
    static MainThreadExecutor provideMainExecutor() {
        return new MainThreadExecutor();
    }

    @Singleton
    @Provides
    static IOExecutor provideIoExecutor() {
        return new IOExecutor();
    }
}
