package com.munch.history.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.history.model.History;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface HistoryDao {

    /**
     * Select all history from the History table.
     *
     * @return all history.
     */
    @Query("SELECT * FROM History ORDER BY timestamp DESC")
    Flowable<List<History>> getHistoryList();

    /**
     * Select limited history count from the History table.
     *
     * @return all history.
     */
    @Query("SELECT * FROM History ORDER BY timestamp DESC LIMIT :selectCount OFFSET :offsetCount")
    Flowable<List<History>> getLastHistory(int selectCount, int offsetCount);

    /**
     * Insert a history in the database. If the history already exists, replace it.
     *
     * @param history the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(@NonNull History history);
}
