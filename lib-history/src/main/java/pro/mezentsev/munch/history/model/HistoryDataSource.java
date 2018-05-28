package pro.mezentsev.munch.history.model;

import android.support.annotation.NonNull;

import pro.mezentsev.munch.history.model.History;

import java.util.List;

import io.reactivex.Flowable;

public interface HistoryDataSource {
    /**
     * @param count
     * @param offset
     * @return history list by count and offset.
     */
    Flowable<List<History>> getLastHistory(int count, int offset);

    /**
     * @return all history list
     */
    Flowable<List<History>> getHistoryList();

    /**
     * Add new history to db.
     * @param history
     */
    void saveHistory(@NonNull History history);

    /**
     * Remove history from database.
     * @param history
     */
    void removeHistory(@NonNull History history);
}
