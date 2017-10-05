package com.munch.browser.web;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

class MunchWebPresenter implements IMunchWebContract.Presenter {
    private static final String TAG = MunchWebPresenter.class.getSimpleName();

    @Nullable
    private IMunchWebContract.View mView;

    MunchWebPresenter() {}

    @Override
    public void attach(@NonNull IMunchWebContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void stop() {

    }

    @Override
    public void back() {

    }

    void loadUrl(@NonNull String url) {
        Log.d(TAG, "Requested url: " + url);
    }
}
