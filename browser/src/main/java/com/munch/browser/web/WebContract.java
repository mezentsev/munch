package com.munch.browser.web;

import android.support.annotation.NonNull;

import com.munch.mvp.MvpContract;
import com.munch.webview.MunchWebContract;

public interface WebContract extends MvpContract {
    interface View extends MvpContract.View {
        void showProgress(int progress);

        void showBackButton(boolean isShow);

        void showForwardButton(boolean isShow);

        void stopRefreshBySwipe();

        void enableRefreshBySwipe(boolean enable);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void attachWebView(@NonNull MunchWebContract.View webView);

        void useUrl(@NonNull String url);

        void goBack();

        void goForward();
    }
}
