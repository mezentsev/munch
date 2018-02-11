package com.munch.browser.web.presentation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.web.WebActivityContract;
import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;
import com.munch.webview.MunchWebContract;

import javax.inject.Inject;

public class WebPresenter implements WebActivityContract.Presenter {

    @NonNull
    private final HistoryDataSource mHistoryDataSource;
    @Nullable
    private WebActivityContract.View mView;
    @Nullable
    private MunchWebContract.View mMunchWebView;

    @Inject
    public WebPresenter(@NonNull HistoryDataSource historyDataSource) {
        mHistoryDataSource = historyDataSource;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void attachView(@NonNull WebActivityContract.View view) {
        mView = view;
    }

    @Override
    public void attachMunchWebView(@NonNull MunchWebContract.View munchWebView) {
        mMunchWebView = munchWebView;
        munchWebView.setProgressListener(new ProgressListener(mHistoryDataSource));
    }

    @Override
    public void detachMunchWebView() {
        mMunchWebView = null;
    }

    @Override
    public void goBack() {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public void goForward() {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public boolean canBack() {
        return false;
    }

    @Override
    public boolean canForward() {
        return false;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void useUrl(@NonNull final String url) {
        if (mMunchWebView != null) {
            String preparedUrl = prepareUrl(url);
            mMunchWebView.openUrl(preparedUrl);
        }
    }

    /**
     * Normalizing url with http and lowercase.
     *
     * @param url
     * @return normalized url
     */
    @NonNull
    private String prepareUrl(@NonNull String url) {
        String lowerUrl = url.toLowerCase();

        if (!lowerUrl.matches("^\\w+?://.*")) {
            lowerUrl = "http://" + lowerUrl;
        }

        return lowerUrl;
    }

    private static class ProgressListener implements MunchWebContract.WebProgressListener {
        private static final String TAG = "[MNCH:PL]";

        @NonNull
        private final HistoryDataSource mHistoryDataSource;

        ProgressListener(@NonNull HistoryDataSource historyDataSource) {
            mHistoryDataSource = historyDataSource;
        }

        @Override
        public void onStart(long timestamp,
                            @NonNull String url) {
            Log.d(TAG, "onStart " + url);
        }

        @Override
        public void onFavicon(long timestamp,
                              @NonNull String url,
                              @NonNull Bitmap favicon) {
            Log.d(TAG, "onFavicon " + url);
        }

        @Override
        public void onFinish(long timestamp,
                             @NonNull String url,
                             @NonNull String title) {
            Log.d(TAG, "onFinish " + url);

            mHistoryDataSource.saveHistory(new History(url, title, timestamp));
        }

        @Override
        public void onError(long timestamp,
                            @NonNull String url,
                            int errorCode) {
            Log.d(TAG, "onError " + url);
        }
    }
}
