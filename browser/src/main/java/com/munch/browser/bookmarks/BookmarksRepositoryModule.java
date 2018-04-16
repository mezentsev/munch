package com.munch.browser.bookmarks;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.munch.bookmarks.data.local.BookmarksDao;
import com.munch.bookmarks.data.local.LocalBookmarksDataSource;
import com.munch.bookmarks.model.BookmarksDataSource;
import com.munch.browser.utils.IOExecutor;
import com.munch.bookmarks.data.local.BookmarksDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class BookmarksRepositoryModule {

    @Singleton
    @Provides
    static BookmarksDatabase provideDb(Application context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                BookmarksDatabase.class,
                "Bookmarks.db")
                .build();
    }

    @Singleton
    @Provides
    static BookmarksDao provideTasksDao(BookmarksDatabase db) {
        return db.bookmarksDao();
    }

    @Singleton
    @Provides
    static BookmarksDataSource provideTasksLocalDataSource(BookmarksDao bookmarksDao) {
        return new LocalBookmarksDataSource(bookmarksDao);
    }

    @Singleton
    @Provides
    static BookmarksRepository provideHistoryRepository(IOExecutor ioExecutor,
                                                        BookmarksDataSource dataSource) {
        return new BookmarksRepository(ioExecutor, dataSource);
    }
}
