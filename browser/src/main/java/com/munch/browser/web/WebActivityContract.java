package com.munch.browser.web;

import android.support.annotation.NonNull;

import com.munch.mvp.MvpContract;
import com.munch.webview.MunchWebContract;

public interface WebActivityContract extends MvpContract {
    interface View extends MvpContract.View {
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void useUrl(@NonNull String url);

        void attachMunchWebView(@NonNull MunchWebContract.View munchWebView);

        void detachMunchWebView();

        void goBack();

        void goForward();

        boolean canBack();

        boolean canForward();
    }
}
