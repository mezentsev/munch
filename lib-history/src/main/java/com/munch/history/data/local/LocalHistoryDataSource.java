package com.munch.history.data.local;

import android.support.annotation.NonNull;

import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Concrete implementation of a data source as a db.
 */
@Singleton
public class LocalHistoryDataSource implements HistoryDataSource {
    @NonNull
    private final HistoryDao mHistoryDao;

    @Inject
    public LocalHistoryDataSource(@NonNull HistoryDao historyDao) {
        mHistoryDao = historyDao;
    }

    @Override
    public Flowable<List<History>> getLastHistory(int count, int offset) {
        return mHistoryDao.getLastHistory(count, offset);
    }

    @Override
    public Flowable<List<History>> getHistoryList() {
        return mHistoryDao.getHistoryList();
    }

    @Override
    public void saveHistory(@NonNull History history) {
        mHistoryDao.insertHistory(history);
    }

    @Override
    public void removeHistory(@NonNull History history) {
        mHistoryDao.removeHistory(history);
    }
}
