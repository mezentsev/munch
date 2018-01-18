package com.munch.history.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;

import java.util.List;

import javax.inject.Inject;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalHistoryDataSource implements HistoryDataSource {

    private final String TAG = "[MNCH:LHDS]";

    @NonNull
    private final HistoryDao mHistoryDao;

    @Inject
    public LocalHistoryDataSource(@NonNull HistoryDao historyDao) {
        mHistoryDao = historyDao;
        Log.d(TAG, "local history data source inited");
    }

    @Override
    public void getHistory(@NonNull final String taskId,
                           @NonNull final GetHistoryCallback callback) {
        Log.d(TAG, "getHistory");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final History history = mHistoryDao.getHistoryById(taskId);
                Log.d(TAG, "getHistory " + history.getUrl());

            /*mAppExecutors.mainThread().execute(() -> {
                if (history != null) {
                    callback.onHistoryLoaded(history);
                } else {
                    callback.onDataNotAvailable();
                }
            });*/
            }
        };

        //mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveHistory(@NonNull final History history) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mHistoryDao.insertHistory(history);
            }
        };
        Log.d(TAG, "saveHistory " + history.getUrl());
        //mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void getHistoryList(@NonNull LoadHistoryCallback callback) {
        Log.d(TAG, "getHistoryList");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<History> historyList = mHistoryDao.getHistory();
                Log.d(TAG, "getHistoryList " + historyList.toString());
            /*mAppExecutors.mainThread().execute((Runnable) () -> {
                if (historyList.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable();
                } else {
                    callback.onHistoryLoaded(historyList);
                }
            });*/
            }
        };

        // TODO: 18.01.18 implement
        callback.onDataNotAvailable();
    }

    @Override
    public void refreshHistory() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteHistory() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mHistoryDao.deleteHistory();
            }
        };

        Log.d(TAG, "deleteHistory");

        //mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteHistoryById(@NonNull final String historyId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mHistoryDao.deleteHistoryById(historyId);
            }
        };

        Log.d(TAG, "deleteHistoryById " + historyId);


        //mAppExecutors.diskIO().execute(deleteRunnable);
    }
}
