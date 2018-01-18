package com.munch.suggest.presentation;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;

import com.munch.suggest.SuggestContract;
import com.munch.suggest.data.SuggestResponse;
import com.munch.suggest.model.QueryInteractor;
import com.munch.suggest.model.RequestSpecification;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SuggestPresenter implements SuggestContract.Presenter {
    private static final String TAG = "[MNCH:SuggestPresenter]";

    @NonNull
    private final static String LANG = "en";
    private final static int MAX = 5;

    @Nullable
    private SuggestInteractor.Factory mSuggestInteractorFactory;
    @Nullable
    private RequestSpecification.Factory mRequestSpecificationFactory;
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
    private int mAttachedTimes = 0;

    public SuggestPresenter() {
    }

    /**
     * Set or unset interactor factory for Suggest Engine.
     * Create {@link RequestSpecification.Factory} object.
     *
     * @param suggestInteractorFactory interactor factory
     */
    @Override
    public void setInteractorFactory(@Nullable SuggestInteractor.Factory suggestInteractorFactory) {
        mSuggestInteractorFactory = suggestInteractorFactory;

        if (suggestInteractorFactory != null) {
            mRequestSpecificationFactory = suggestInteractorFactory.get().getSpecificationFactory();
        } else {
            mRequestSpecificationFactory = null;
        }
    }

    /**
     * Set current user query and make request to Suggest Engine by {@link SuggestInteractor}
     *
     * @param query user query
     */
    @UiThread
    public void setQuery(@Nullable final String query) {
        if (mSuggestInteractorFactory == null ||
                mRequestSpecificationFactory == null) {
            Log.d(TAG, "Suggest Interator not defined");
            return;
        }

        mCurrentUserQuery = query;

        mCompositeDisposable.clear();

        RequestSpecification specification = mRequestSpecificationFactory.get(
                query,
                MAX,
                LANG);

        Single<SuggestResponse> queryObservable = mQueryInteractor.getSuggests(specification)
                .onErrorReturnItem(SuggestResponse.empty());

        mCompositeDisposable.add(
                Single.concat(
                        queryObservable,
                        Single.zip(
                                queryObservable,
                                mSuggestInteractorFactory.get().getSuggests(specification)
                                        .onErrorReturnItem(SuggestResponse.empty()),
                                zipResponses(query)
                        )
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<SuggestResponse>() {
                                    @Override
                                    public void accept(SuggestResponse suggestResponse) throws Exception {
                                        SuggestPresenter.this.showSuggests(suggestResponse);
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.e(TAG, "Error", throwable);
                                    }
                                },
                                new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        Log.d(TAG, "Completed: " + query);
                                    }
                                }
                        ));
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
        if (mAttachedTimes == 0) {
            onFirstAttachView();
        }

        ++mAttachedTimes;
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

    private static BiFunction<SuggestResponse, SuggestResponse, SuggestResponse> zipResponses(@Nullable final String query) {
        return new BiFunction<SuggestResponse, SuggestResponse, SuggestResponse>() {
            @Override
            public SuggestResponse apply(SuggestResponse queryResponse, SuggestResponse interactorResponse) throws Exception {
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
                                Uri queryUrl = querySuggest.getUrl();
                                String queryTitle = querySuggest.getTitle().toLowerCase();
                                String suggestTitle = suggest.getTitle().toLowerCase();

                                Uri suggestUrl = suggest.getUrl();

                                if (queryTitle.equals(suggestTitle) ||
                                        (queryUrl != null && queryUrl.equals(suggestUrl))) {
                                    exists = true;
                                    break;
                                }
                            }

                            // skip if already same suggest exists
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
                        interactorResponse.getSearchBaseUrl(),
                        suggests
                );
            }
        };
    }
}
