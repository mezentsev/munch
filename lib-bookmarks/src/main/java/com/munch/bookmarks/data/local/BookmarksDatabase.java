package com.munch.bookmarks.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.munch.bookmarks.model.Bookmark;

/**
 * The Room Database that contains the History table.
 */
@Database(entities = {Bookmark.class}, version = 1)
public abstract class BookmarksDatabase extends RoomDatabase {

    @NonNull
    public abstract BookmarksDao bookmarksDao();
}
