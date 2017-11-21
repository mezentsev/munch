package com.munch.suggest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.munch.suggest.R;
import com.munch.suggest.SuggestContract;
import com.munch.suggest.data.SuggestClicklistener;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;
import com.munch.suggest.presenter.SuggestPresenter;

import java.util.List;

public class SuggestView extends LinearLayout implements SuggestContract.View {
    private static final String TAG = SuggestView.class.getSimpleName();

    @NonNull
    private final SuggestAdapter mSuggestAdapter;

    @Nullable
    private SuggestContract.Presenter mSuggestPresenter;
    @NonNull
    private RecyclerView mRecyclerView;

    public SuggestView(@NonNull Context context) {
        this(context, null);
    }

    public SuggestView(@NonNull Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuggestView(@NonNull Context context,
                       @Nullable AttributeSet attrs,
                       int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mSuggestAdapter = new SuggestAdapter(context);

        View view = inflate(context, R.layout.munch_suggest_view, this);
        mRecyclerView = view.findViewById(R.id.munch_suggest_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mSuggestAdapter);

        if (mSuggestPresenter == null) {
            mSuggestPresenter = new SuggestPresenter();
            mSuggestPresenter.onCreate();
        }
    }

    /**
     * Update list of suggests on ui.
     *
     * @param suggests list of suggests
     */
    @UiThread
    @Override
    public void setSuggests(@Nullable String candidate,
                            @Nullable List<Suggest> suggests) {
        Log.d(TAG, "Candidate is: " + candidate);
        mSuggestAdapter.setSuggests(suggests);
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * Set factory for Suggest Engine.
     *
     * @param suggestInteractorFactory suggest interactor factory
     */
    @UiThread
    @Override
    public void setSuggestInteractor(@NonNull SuggestInteractor.Factory suggestInteractorFactory) {
        if (mSuggestPresenter != null) {
            mSuggestPresenter.setInteractorFactory(suggestInteractorFactory);
        } else {
            Log.e(TAG, "Presenter is not defined");
        }
    }

    /**
     * Set user query.
     *
     * @param query user query
     */
    @UiThread
    @Override
    public void setUserQuery(@Nullable String query) {
        if (mSuggestPresenter != null) {
            mSuggestPresenter.setQuery(query);
        } else {
            Log.e(TAG, "Presenter is not defined");
        }
    }

    /**
     * Set or unset suggest click listener.
     *
     * @param suggestClickListener click listener
     */
    @UiThread
    @Override
    public void setSuggestClickListener(@Nullable SuggestClicklistener suggestClickListener) {
        mSuggestAdapter.setClickListener(suggestClickListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mSuggestPresenter != null) {
            mSuggestPresenter.attachView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSuggestPresenter != null) {
            mSuggestPresenter.detachView();
        }
    }
}
