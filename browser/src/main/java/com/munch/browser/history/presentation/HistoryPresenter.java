package com.munch.browser.history.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.history.HistoryContract;
import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;
import com.munch.history.model.HistoryRepository;

import java.util.List;

import javax.inject.Inject;

public final class HistoryPresenter implements HistoryContract.Presenter {

    @NonNull
    private final String TAG = "[MNCH:HPresenter]";

    @Inject
    HistoryRepository mHistoryRepository;

    @Nullable
    private HistoryContract.View mView;

    @Inject
    public HistoryPresenter() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void attachView(@NonNull HistoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadHistory() {
        mHistoryRepository.getHistoryList(new HistoryDataSource.LoadHistoryCallback() {
            @Override
            public void onHistoryLoaded(@NonNull List<History> historyList) {
                if (mView != null) {
                    mView.informHistoryLoad(historyList.size());
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable");

                if (mView != null) {
                    mView.informHistoryLoad(0);
                }
            }
        });
    }
}
