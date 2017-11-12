package com.munch.suggest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.munch.helpers.Lazy;
import com.munch.suggest.SuggestContract;
import com.munch.suggest.model.Suggest;
import com.munch.suggest.model.SuggestInteractor;
import com.munch.suggest.model.YaSuggestInteractor;
import com.munch.suggest.presenter.SuggestPresenter;

import java.util.List;

public class SuggestView extends LinearLayout implements SuggestContract.View {
    private static final String TAG = SuggestView.class.getSimpleName();

    @Nullable
    private SuggestContract.Presenter mSuggestPresenter;
    @NonNull
    private Lazy<SuggestInteractor> mSuggestInteractorLazy;

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
        mSuggestInteractorLazy = new YaSuggestInteractor.LazyImpl();
    }

    /**
     * Update list of suggests on ui.
     *
     * @param suggests list of suggests
     */
    @UiThread
    public void setSuggests(@Nullable List<Suggest> suggests) {
        Log.d(TAG, "Suggests: " + suggests);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mSuggestPresenter == null) {
            mSuggestPresenter = new SuggestPresenter(mSuggestInteractorLazy);
            mSuggestPresenter.onCreate();
        }

        mSuggestPresenter.attachView(this);
        mSuggestPresenter.setQuery("котики");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSuggestPresenter != null) {
            mSuggestPresenter.detachView();
        }
    }
}
