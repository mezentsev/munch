package com.munch.history;

import android.support.annotation.NonNull;

import com.munch.history.model.History;

import java.util.List;

import io.reactivex.Flowable;

public interface HistoryRepository {

    /**
     * Fetch history list.
     */
    Flowable<List<History>> getHistory();

    /**
     * Insert new history to db.
     */
    void saveHistory(@NonNull History history);

    /**
     * Remove history from list.
     */
    void removeHistory(@NonNull History history);
}
