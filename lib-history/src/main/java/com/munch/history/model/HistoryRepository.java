package com.munch.history.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Implementation to load history from database.
 */
public final class HistoryRepository implements HistoryDataSource {

    private final String TAG = "[MNCH:HRepository]";

    @NonNull
    private final HistoryDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, History> mCachedHistory;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    @Inject
    public HistoryRepository(@Local @NonNull HistoryDataSource localDataSource) {
        mLocalDataSource = localDataSource;
        Log.d(TAG, "history repository inited");
    }

    @Override
    public void getHistoryList(@NonNull final LoadHistoryCallback callback) {
        Log.d(TAG, "getHistoryList");

        if (mCachedHistory != null && !mCacheIsDirty) {
            callback.onHistoryLoaded(new ArrayList<>(mCachedHistory.values()));
        }

        if (mCacheIsDirty) {
            // maybe load from remote?
        } else {
            // local storage
            mLocalDataSource.getHistoryList(new LoadHistoryCallback() {
                @Override
                public void onHistoryLoaded(@NonNull List<History> historyList) {
                    Log.d(TAG, "onHistoryLoaded");

                    refreshCache(historyList);
                    callback.onHistoryLoaded(historyList);
                }

                @Override
                public void onDataNotAvailable() {
                    Log.d(TAG, "onDataNotAvailable");

                    // TODO: 18.01.18 maybe load from remote?
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void getHistory(@NonNull String historyId,
                           @NonNull GetHistoryCallback callback) {

    }

    @Override
    public void saveHistory(@NonNull History history) {

    }

    @Override
    public void refreshHistory() {

    }

    @Override
    public void deleteHistory() {

    }

    @Override
    public void deleteHistoryById(@NonNull String historyId) {

    }

    /**
     * Update cache with new history list.
     *
     * @param historyList
     */
    private void refreshCache(@NonNull List<History> historyList) {
        if (mCachedHistory == null) {
            mCachedHistory = new LinkedHashMap<>();
        }

        mCachedHistory.clear();

        for (History history : historyList) {
            mCachedHistory.put(history.getId(), history);
        }

        mCacheIsDirty = false;
        Log.d(TAG, "refreshCache");
    }
}
