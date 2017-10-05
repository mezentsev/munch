package com.munch.browser.web;

import android.support.annotation.NonNull;

import com.munch.browser.mvp.IPresenter;

public interface IMunchWebContract {
    interface View {
        void loadUrl(@NonNull String url);
    }

    interface Presenter extends IPresenter<View> {
        void stop();
        void back();
    }
}
