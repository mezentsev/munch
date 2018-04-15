package com.munch.bookmarks;

import android.support.annotation.NonNull;

import com.munch.bookmarks.model.Bookmark;

import java.util.List;

import io.reactivex.Flowable;

public interface BookmarksRepository {

    /**
     * Fetch history list.
     */
    Flowable<List<Bookmark>> getBookmarks();

    /**
     * Insert new bookmark to db.
     */
    void saveBookmark(@NonNull Bookmark history);

    /**
     * Remove history from list.
     */
    void removeBookmark(@NonNull Bookmark history);
}
