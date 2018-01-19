package com.munch.history.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.munch.history.model.History;

import java.util.List;

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
    @Query("SELECT * FROM History")
    List<History> getHistory();

    /**
     * Select a History by id.
     *
     * @param historyId the History id.
     * @return the history with historyId.
     */
    @Query("SELECT * FROM History WHERE id = :historyId")
    History getHistoryById(@NonNull String historyId);

    /**
     * Insert a history in the database. If the history already exists, replace it.
     *
     * @param history the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory(@NonNull History history);

    /**
     * Update a history.
     *
     * @param history task to be updated
     * @return the number of tasks updated. This should always be 1.
     */
    @Update
    int updateHistory(@NonNull History history);

    /**
     * Delete a history by id.
     *
     * @return the number of history deleted. This should always be 1.
     */
    @Query("DELETE FROM History WHERE id = :historyId")
    int deleteHistoryById(@NonNull String historyId);

    /**
     * Delete all history.
     */
    @Query("DELETE FROM History")
    void deleteHistory();
}
