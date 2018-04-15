package com.munch.webview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.munch.mvp.MvpContract;

public interface WebContract extends MvpContract {
    interface View extends MvpContract.View {
        /**
         * Get webview.
         */
        @NonNull
        WebView obtainWebView();

        /**
         * Load site by url from internet or cache.
         *
         * @param url
         */
        void loadUrl(@NonNull String url);

        /**
         * Add progress listener to list.
         *
         * @param progressListener
         */
        void addProgressListener(@NonNull WebProgressListener progressListener);

        /**
         * Remove progress listener from list.
         *
         * @param progressListener
         */
        void removeProgressListener(@NonNull WebProgressListener progressListener);

        /**
         * Set scroll change listener.
         *
         * @param scrollListener
         */
        void setScrollListener(@Nullable ScrollListener scrollListener);

        /**
         * @return can go back on history state.
         */
        boolean canGoBack();

        /**
         * @return can go forward on history state.
         */
        boolean canGoForward();

        /**
         * Go back on history state.
         */
        void goBack();

        /**
         * Go forward on history state.
         */
        void goForward();

        /**
         * Reload web page.
         */
        void reload();
    }

    interface WebArchiveListener {
        /**
         * @param name saved file name
         */
        void onWebArchiveSaved(@NonNull String name);
    }

    interface ScrollListener {
        /**
         * @param l - Current horizontal scroll origin.
         * @param t - Current vertical scroll origin.
         * @param oldl - Previous horizontal scroll origin.
         * @param oldt - Previous vertical scroll origin.
         */
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
