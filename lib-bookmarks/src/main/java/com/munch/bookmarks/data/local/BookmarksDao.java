package com.munch.bookmarks.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.munch.bookmarks.model.Bookmark;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface BookmarksDao {

    /**
     * Select all bookmarks from the Boorkars table.
     *
     * @return all bookmarks.
     */
    @Query("SELECT * FROM Bookmarks ORDER BY timestamp DESC")
    Flowable<List<Bookmark>> getBookmarkList();

    /**
     * Select limited bookmarks count from the Bookmarks table.
     *
     * @return all bookmarks.
     */
    @Query("SELECT * FROM Bookmarks ORDER BY timestamp DESC LIMIT :selectCount OFFSET :offsetCount")
    Flowable<List<Bookmark>> getLastBookmarks(int selectCount, int offsetCount);

    /**
     * Insert a bookmark in the database. If the bookmark already exists, replace it.
     *
     * @param bookmark the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBookmark(@NonNull Bookmark bookmark);

    /**
     * Remove bookmark form database.
     *
     * @param bookmark the bookmark to be removed.
     */
    @Delete
    void removeBookmark(@NonNull Bookmark bookmark);
}
