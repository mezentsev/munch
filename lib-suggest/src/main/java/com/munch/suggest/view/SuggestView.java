package com.munch.suggest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.munch.suggest.SuggestContract;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;
import com.munch.suggest.presenter.SuggestPresenter;

import java.util.List;

public class SuggestView extends LinearLayout implements SuggestContract.View {
    private static final String TAG = SuggestView.class.getSimpleName();

    @Nullable
    private SuggestContract.Presenter mSuggestPresenter;
    @NonNull
    private Toast mToast;

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
        if (mSuggestPresenter == null) {
            mSuggestPresenter = new SuggestPresenter();
            mSuggestPresenter.onCreate();
        }

        mToast = new Toast(getContext());
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
        Log.d(TAG, "Suggests: " + suggests);

        mToast.cancel();
        mToast = Toast.makeText(getContext(), candidate, Toast.LENGTH_SHORT);
        mToast.show();
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
