package com.munch.browser.di;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.munch.bookmarks.data.local.BookmarksDao;
import com.munch.bookmarks.data.local.LocalBookmarksDataSource;
import com.munch.bookmarks.model.Bookmark;
import com.munch.bookmarks.model.BookmarksDataSource;
import com.munch.browser.bookmarks.BookmarksRepository;
import com.munch.browser.utils.IOExecutor;
import com.munch.bookmarks.data.local.BookmarksDatabase;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BookmarksRepositoryModule {

    @NonNull
    private final static List<Bookmark> sBookmarkList = Arrays.asList(
            new Bookmark("https://google.com", "Google"),
            new Bookmark("https://facebook.com", "Facebook"),
            new Bookmark("https://twitter.com", "Twitter"),
            new Bookmark("https://instagram.com", "Instagram"),
            new Bookmark("https://youtube.com", "Youtube")
    );

    @NonNull
    private final static String sDbName = "Bookmarks.db";
    @NonNull
    private static BookmarksDao mDao;


    @Singleton
    @Provides
    static BookmarksDatabase provideDb(final Application application,
                                final IOExecutor executor) {
        return Room.databaseBuilder(
                application.getApplicationContext(),
                BookmarksDatabase.class,
                sDbName)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                for (Bookmark bookmark : sBookmarkList) {
                                    mDao.insertBookmark(bookmark);
                                }
                            }
                        });

                        Log.d("DAO", "onCreate");
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    static BookmarksDao provideTasksDao(BookmarksDatabase db) {
        mDao = db.bookmarksDao();
        return mDao;
    }

    @Singleton
    @Provides
    @Named(value = "local")
    static BookmarksDataSource provideTasksLocalDataSource(BookmarksDao bookmarksDao) {
        return new LocalBookmarksDataSource(bookmarksDao);
    }

    @Singleton
    @Provides
    static BookmarksRepository provideHistoryRepository(IOExecutor ioExecutor,
                                                        @Named(value = "local") BookmarksDataSource dataSource) {
        return new BookmarksRepository(ioExecutor, dataSource);
    }
}
