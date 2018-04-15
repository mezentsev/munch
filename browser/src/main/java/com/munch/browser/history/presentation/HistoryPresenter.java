package com.munch.browser.history.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.history.HistoryContract;
import com.munch.history.model.History;
import com.munch.mvp.FlowableRepository;
import com.munch.mvp.FragmentScoped;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@FragmentScoped
public final class HistoryPresenter implements HistoryContract.Presenter {

    private final static int HISTORY_COUNT = 10;
    private final static int HISTORY_OFFSET = 0;

    @NonNull
    private final String TAG = "[MNCH:HPresenter]";

    @NonNull
    private final FlowableRepository<History> mHistoryRepository;
    @NonNull
    private final Executor mMainThreadExecutor;

    @Nullable
    private HistoryContract.View mView;

    @Inject
    public HistoryPresenter(@NonNull FlowableRepository<History> historyRepository,
                            @NonNull Executor mainThreadExecutor) {
        mHistoryRepository = historyRepository;
        mMainThreadExecutor = mainThreadExecutor;
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
        Log.d(TAG, "Try to load history");

        DisposableSubscriber<List<History>> disposableSubscriber = new DisposableSubscriber<List<History>>() {
            @Override
            public void onNext(List<History> historyList) {
                if (mView != null) {
                    mView.showHistory(historyList);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mHistoryRepository.get()
                .observeOn(Schedulers.from(mMainThreadExecutor))
                .take(1)
                .subscribe(disposableSubscriber);
    }

    @Override
    public void removeHistory(@NonNull History history) {
        mHistoryRepository.remove(history);
    }
}
