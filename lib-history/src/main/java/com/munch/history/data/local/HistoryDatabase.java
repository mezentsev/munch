package com.munch.history.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.munch.history.model.History;

/**
 * The Room Database that contains the History table.
 */
@Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {

    @NonNull
    public abstract HistoryDao historyDao();
}
