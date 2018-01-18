package com.munch.browser.history;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.munch.history.data.local.HistoryDao;
import com.munch.history.data.local.HistoryDatabase;
import com.munch.history.data.local.LocalHistoryDataSource;
import com.munch.history.model.HistoryDataSource;
import com.munch.history.model.Local;

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
    @Binds
    @Local
    abstract HistoryDataSource provideTasksLocalDataSource(LocalHistoryDataSource localHistoryDataSource);

    /*@Singleton
    @Binds
    @Remote
    abstract HistoryDataSource provideTasksRemoteDataSource(FakeTasksRemoteDataSource dataSource);*/

    /*@Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new AppExecutors.MainThreadExecutor());
    }*/
}
