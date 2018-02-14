package com.munch.webview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.munch.mvp.MvpContract;

public interface MunchWebContract extends MvpContract {
    interface View extends MvpContract.View {
        /**
         * Load site by url from internet or cache.
         *
         * @param url
         */
        void loadUrl(@NonNull String url);

        /**
         * Load html in to webView.
         *
         * @param html
         */
        void loadHtml(@NonNull String html);

        /**
         * Set progress listener.
         *
         * @param progressListener
         */
        void setProgressListener(@Nullable WebProgressListener progressListener);
    }
}
