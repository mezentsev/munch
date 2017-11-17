package com.munch.suggest.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.munch.suggest.SuggestContract;
import com.munch.suggest.data.SuggestResponse;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SuggestPresenter implements SuggestContract.Presenter {
    private static final String TAG = SuggestPresenter.class.getSimpleName();

    @NonNull
    private SuggestInteractor.Factory mSuggestInteractorFactory;
    @Nullable
    private SuggestContract.View mView;
    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Nullable
    private String mCurrentUserQuery;
    @Nullable
    private List<Suggest> mCurrentSuggests;
    private int mAttachedCount = 0;

    public SuggestPresenter(@NonNull SuggestInteractor.Factory suggestInteractorFactory) {
        setInteractorFactory(suggestInteractorFactory);
    }

    @Override
    public void setInteractorFactory(@NonNull SuggestInteractor.Factory suggestInteractorFactory) {
        mSuggestInteractorFactory = suggestInteractorFactory;
    }

    @UiThread
    public void setQuery(@NonNull String query) {
        mCurrentUserQuery = query;

        mCompositeDisposable.clear();
        mCompositeDisposable.add(
                mSuggestInteractorFactory.get().getSuggests(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableObserver<SuggestResponse>() {
                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                showSuggests(null);
                            }

                            @Override
                            public void onNext(@NonNull SuggestResponse suggests) {
                                showSuggests(suggests.getSuggests());
                            }
                        }));
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onFirstAttachView() {
        Log.d(TAG, "onFirstAttachView");
    }

    @Override
    public void attachView(@NonNull SuggestContract.View view) {
        if (mAttachedCount == 0) {
            onFirstAttachView();
        }

        ++mAttachedCount;
        mView = view;
        showSuggests(mCurrentSuggests);
    }

    @Override
    public void detachView() {
        mView = null;
        mCompositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    @UiThread
    private void showSuggests(@Nullable List<Suggest> suggests) {
        if (mView != null) {
            // TODO: 17.11.17 compare curUserQuery and decide: append or replace
            mCurrentSuggests = suggests;
            mView.setSuggests(mCurrentSuggests);
        }
    }
}
