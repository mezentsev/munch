package com.munch.history.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * Concrete implementation of a data source as a db.
 */
public class LocalHistoryDataSource implements HistoryDataSource {

    private final String TAG = "[MNCH:LHDS]";

    @NonNull
    private final Executor mIoExecutor;
    @NonNull
    private final Executor mMainExecutor;
    @NonNull
    private final HistoryDao mHistoryDao;

    public LocalHistoryDataSource(@NonNull HistoryDao historyDao,
                                  @NonNull Executor ioExecutor,
                                  @NonNull Executor mainExecutor) {
        mIoExecutor = ioExecutor;
        mMainExecutor = mainExecutor;
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
                Log.d(TAG, "getHistory " + (history != null ? history.getUrl() : ""));

                mMainExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (history != null) {
                            callback.onHistoryLoaded(history);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mIoExecutor.execute(runnable);
    }

    @Override
    public void saveHistory(@NonNull final History history) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mHistoryDao.insertHistory(history);
            }
        };

        mIoExecutor.execute(saveRunnable);

        Log.d(TAG, "saveHistory " + history.getUrl());
    }

    @Override
    public void getLastHistoryList(final int count,
                                   final int offset,
                                   @NonNull final LoadHistoryCallback callback) {
        Log.d(TAG, "getLastHistoryList");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<History> historyList = mHistoryDao.getLastHistory(count, offset);
                Log.d(TAG, "getLastHistoryList " + historyList.toString());

                mMainExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (historyList.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onHistoryLoaded(historyList);
                        }
                    }
                });
            }
        };

        mIoExecutor.execute(runnable);
    }

    @Override
    public void getHistoryList(@NonNull final LoadHistoryCallback callback) {
        Log.d(TAG, "getHistoryList");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<History> historyList = mHistoryDao.getHistory();
                Log.d(TAG, "getHistoryList " + historyList.toString());

                mMainExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (historyList.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onHistoryLoaded(historyList);
                        }
                    }
                });
            }
        };

        mIoExecutor.execute(runnable);
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
    }
}
