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
     * Select all history from the History table.
     *
     * @return all history.
     */
    @Query("SELECT * FROM Bookmark ORDER BY timestamp DESC")
    Flowable<List<Bookmark>> getHistoryList();

    /**
     * Select limited history count from the History table.
     *
     * @return all history.
     */
    @Query("SELECT * FROM Bookmark ORDER BY timestamp DESC LIMIT :selectCount OFFSET :offsetCount")
    Flowable<List<Bookmark>> getLastHistory(int selectCount, int offsetCount);

    /**
     * Insert a history in the database. If the history already exists, replace it.
     *
     * @param history the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(@NonNull Bookmark history);

    /**
     * Remove history form database.
     *
     * @param history the task to be inserted.
     */
    @Delete
    void removeHistory(@NonNull Bookmark history);
}
