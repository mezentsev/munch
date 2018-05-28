package pro.mezentsev.munch.browser.history.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pro.mezentsev.munch.browser.history.HistoryContract;
import pro.mezentsev.munch.history.model.History;
import pro.mezentsev.munch.mvp.FlowableRepository;
import pro.mezentsev.munch.mvp.FragmentScoped;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@FragmentScoped
public final class HistoryPresenter implements HistoryContract.Presenter {

    @NonNull
    private final String TAG = "[MNCH:HPresenter]";

    @NonNull
    private final FlowableRepository<History> mHistoryRepository;
    @NonNull
    private final Executor mMainThreadExecutor;
    @NonNull
    private final CompositeDisposable mCompositeDisposable;

    @Nullable
    private HistoryContract.View mView;

    @Inject
    public HistoryPresenter(@NonNull FlowableRepository<History> historyRepository,
                            @NonNull Executor mainThreadExecutor) {
        mHistoryRepository = historyRepository;
        mMainThreadExecutor = mainThreadExecutor;

        mCompositeDisposable = new CompositeDisposable();
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
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadAll() {
        Log.d(TAG, "Try to load history");

        mCompositeDisposable.clear();
        mCompositeDisposable.add(
                mHistoryRepository.get()
                        .observeOn(Schedulers.from(mMainThreadExecutor))
                        .take(1)
                        .subscribeWith(new DisposableSubscriber<List<History>>() {
                            @Override
                            public void onNext(List<History> historyList) {
                                if (mView != null) {
                                    mView.show(historyList);
                                } else {
                                    Log.d(TAG, "No view attached. Ignored.");
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Load failed", e);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Load complete");
                            }
                        })
        );
    }

    @Override
    public void remove(@NonNull History history) {
        mHistoryRepository.remove(history);
    }
}
