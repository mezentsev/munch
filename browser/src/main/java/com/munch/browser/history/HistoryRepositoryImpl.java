package com.munch.browser.history;

import android.support.annotation.NonNull;
import android.util.Log;

import com.munch.history.HistoryRepository;
import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Implementation to load history from database.
 */
@Singleton
final class HistoryRepositoryImpl implements HistoryRepository {
    @NonNull
    private final Executor mExecutor;
    @NonNull
    private final HistoryDataSource mLocalDataSource;
    @NonNull
    private static final String TAG = "[HistoryRepoImpl]";

    @Inject
    public HistoryRepositoryImpl(@NonNull Executor executor,
                                 @NonNull HistoryDataSource localDataSource) {
        mExecutor = executor;
        mLocalDataSource = localDataSource;
    }

    /**
     * Fetch history list.
     */
    @Override
    public Flowable<List<History>> getHistory() {
        return mLocalDataSource.getHistoryList();
    }

    /**
     * Insert new history to db.
     */
    @Override
    public void saveHistory(@NonNull final History history) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Saving history: title=" + history.getTitle() +
                "; url=" + history.getUrl() + "; isFavicon=" + (history.getFavicon() != null));

                mLocalDataSource.saveHistory(history);
            }
        });
    }
}
