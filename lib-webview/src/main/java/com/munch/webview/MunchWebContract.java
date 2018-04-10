package com.munch.webview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View.OnScrollChangeListener;

import com.munch.history.HistoryRepository;
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
         * Set progress listener.
         *
         * @param progressListener
         */
        void setProgressListener(@Nullable WebProgressListener progressListener);

        /**
         * Save web view to file.
         *
         * @param webArchiveListener save listener
         */
        void setWebArchiveListener(@NonNull WebArchiveListener webArchiveListener);

        void setHistoryRepository(@NonNull HistoryRepository historyRepository);

        /**
         * Set scroll change listener.
         *
         * @param scrollListener
         */
        void setScrollListener(@NonNull ScrollListener scrollListener);

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
