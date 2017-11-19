package com.munch.suggest.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.munch.suggest.SuggestContract;
import com.munch.suggest.data.SuggestResponse;
import com.munch.suggest.model.QueryInteractor;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SuggestPresenter implements SuggestContract.Presenter {
    private static final String TAG = SuggestPresenter.class.getSimpleName();

    @Nullable
    private SuggestInteractor.Factory mSuggestInteractorFactory;
    @NonNull
    private SuggestInteractor mQueryInteractor = new QueryInteractor();

    @Nullable
    private SuggestContract.View mView;
    @NonNull
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Nullable
    private String mCurrentUserQuery;
    @Nullable
    private SuggestResponse mSuggestResponse;
    private int mAttachedCount = 0;

    public SuggestPresenter() {
    }

    /**
     * Set or unset interactor factory for Suggest Engine.
     *
     * @param suggestInteractorFactory interactor factory
     */
    @Override
    public void setInteractorFactory(@Nullable SuggestInteractor.Factory suggestInteractorFactory) {
        mSuggestInteractorFactory = suggestInteractorFactory;
    }

    /**
     * Set current user query and make request to Suggest Engine by {@link SuggestInteractor}
     *
     * @param query user query
     */
    @UiThread
    public void setQuery(@Nullable String query) {
        if (mSuggestInteractorFactory == null) {
            Log.d(TAG, "Suggest Interator not defined");
            return;
        }

        mCurrentUserQuery = query;

        mCompositeDisposable.clear();

        mCompositeDisposable.add(
                Observable.concat(
                        mQueryInteractor.getSuggests(query)
                                .onErrorReturnItem(SuggestResponse.empty()),
                        Observable.zip(
                                mQueryInteractor.getSuggests(query)
                                        .onErrorReturnItem(SuggestResponse.empty()),
                                mSuggestInteractorFactory.get().getSuggests(query)
                                        .onErrorReturnItem(SuggestResponse.empty()),
                                (queryResponse, interactorResponse) -> {
                                    List<Suggest> querySuggests = queryResponse.getSuggests();
                                    List<Suggest> interactorSuggests = interactorResponse.getSuggests();

                                    List<Suggest> suggests = null;

                                    // need to concat suggests responses
                                    if (querySuggests != null) {
                                        suggests = new ArrayList<>(querySuggests.size());
                                        suggests.addAll(querySuggests);
                                    }

                                    if (interactorSuggests != null) {
                                        if (suggests == null) {
                                            suggests = new ArrayList<>(interactorSuggests.size());
                                        }

                                        if (querySuggests != null) {
                                            // find exists suggests and skip them
                                            for (Suggest suggest : interactorSuggests) {
                                                boolean exists = false;
                                                for (Suggest querySuggest : querySuggests) {
                                                    if (querySuggest.getUrl().equals(suggest.getUrl()) ||
                                                            querySuggest.getTitle().equals(suggest.getTitle())) {
                                                        exists = true;
                                                        break;
                                                    }
                                                }

                                                if (!exists) {
                                                    suggests.add(suggest);
                                                }
                                            }
                                        } else {
                                            suggests.addAll(interactorSuggests);
                                        }

                                    }

                                    return new SuggestResponse(
                                            query,
                                            interactorResponse.getCandidate(),
                                            suggests
                                    );
                                }
                        )
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SuggestResponse>() {
                            @Override
                            public void onComplete() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error", e);
                                showSuggests(null);
                            }

                            @Override
                            public void onNext(@NonNull SuggestResponse suggests) {
                                showSuggests(suggests);
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

        showSuggests(mSuggestResponse);
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
    private void showSuggests(@Nullable SuggestResponse suggestResponse) {
        if (mView != null) {
            mSuggestResponse = suggestResponse;

            String candidate = null;
            List<Suggest> suggests = null;
            if (suggestResponse != null) {
                candidate = suggestResponse.getCandidate();
                suggests = suggestResponse.getSuggests();
            }

            mView.setSuggests(
                    candidate,
                    suggests
            );
        }
    }
}
