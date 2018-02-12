package com.munch.history.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface HistoryDataSource {
    interface LoadHistoryCallback {

        void onHistoryLoaded(@NonNull List<History> historyList);

        void onDataNotAvailable();
    }

    interface GetHistoryCallback {

        void onHistoryLoaded(@Nullable History history);

        void onDataNotAvailable();
    }

    void getLastHistoryList(int count, int offset, @NonNull LoadHistoryCallback callback);

    void getHistoryList(@NonNull LoadHistoryCallback callback);

    void getHistory(@NonNull String historyId,
                    @NonNull GetHistoryCallback callback);

    void saveHistory(@NonNull History history);

    void refreshHistory();

    void deleteHistory();

    void deleteHistoryById(@NonNull String historyId);
}
