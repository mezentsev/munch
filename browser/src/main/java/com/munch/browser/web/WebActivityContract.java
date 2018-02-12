package com.munch.browser.web;

import android.support.annotation.NonNull;

import com.munch.mvp.MvpContract;
import com.munch.webview.MunchWebView;

public interface WebActivityContract extends MvpContract {
    interface Presenter extends MvpContract.Presenter<MunchWebView> {
        void useUrl(@NonNull String url);

        void goBack();

        void goForward();

        boolean canBack();

        boolean canForward();
    }
}
