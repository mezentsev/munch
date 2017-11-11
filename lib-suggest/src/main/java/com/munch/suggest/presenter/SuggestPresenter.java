package com.munch.suggest.presenter;

import android.support.annotation.NonNull;

import com.munch.mvp.MvpContract;
import com.munch.suggest.model.SuggestInteractor;

class SuggestPresenter extends MvpContract.SimplePresenter {
    private static final String TAG = SuggestPresenter.class.getSimpleName();

    @NonNull
    private SuggestInteractor mSuggestInteractor;

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void onDestroy() {

    }
}
