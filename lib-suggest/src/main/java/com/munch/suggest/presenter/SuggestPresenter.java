package com.munch.suggest.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.munch.helpers.Lazy;
import com.munch.suggest.SuggestContract;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;

public class SuggestPresenter implements SuggestContract.Presenter {
    private static final String TAG = SuggestPresenter.class.getSimpleName();

    @NonNull
    private Lazy<SuggestInteractor> mSuggestInteractorLazy;
    @Nullable
    private SuggestContract.View mView;
    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public SuggestPresenter(@NonNull Lazy<SuggestInteractor> suggestInteractorLazy) {
        mSuggestInteractorLazy = suggestInteractorLazy;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void attachView(@NonNull SuggestContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
    }

    @UiThread
    public void setQuery(@NonNull String query) {
        SuggestInteractor suggestInteractor = mSuggestInteractorLazy.get();

        mCompositeDisposable.clear();

        Observable<List<Suggest>> suggestsObservable = Observable.defer(() -> {
            Request request = mSuggestInteractorLazy.get().getSuggests(query).request();

            // TODO: 12.11.17 irbis parse
            return Observable.just(new ArrayList<>());
        });

        mCompositeDisposable.add(
                suggestsObservable
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<List<Suggest>>() {
                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                updateSuggests(null);
                            }

                            @Override
                            public void onNext(@Nullable List<Suggest> suggests) {
                                updateSuggests(suggests);
                            }
                        }));
    }

    @UiThread
    private void updateSuggests(@Nullable List<Suggest> suggests) {
        if (mView != null) {
            mView.setSuggests(suggests);
        }
    }
}
