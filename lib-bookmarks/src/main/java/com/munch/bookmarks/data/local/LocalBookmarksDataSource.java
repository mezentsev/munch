package com.munch.bookmarks.data.local;

import android.support.annotation.NonNull;

import com.munch.bookmarks.model.Bookmark;
import com.munch.bookmarks.model.BookmarksDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
public class LocalBookmarksDataSource implements BookmarksDataSource {
    @NonNull
    private final BookmarksDao mBookmarksDao;

    @Inject
    public LocalBookmarksDataSource(@NonNull BookmarksDao historyDao) {
        mBookmarksDao = historyDao;
    }

    @Override
    public Flowable<List<Bookmark>> getBookmarksList() {
        return mBookmarksDao.getHistoryList();
    }

    @Override
    public void saveBookmark(@NonNull Bookmark history) {
        mBookmarksDao.insertHistory(history);
    }

    @Override
    public void removeBookmark(@NonNull Bookmark history) {
        mBookmarksDao.removeHistory(history);
    }
}
