package com.munch.browser.history;

import android.support.annotation.NonNull;

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
                mLocalDataSource.saveHistory(history);
            }
        });
    }
}
