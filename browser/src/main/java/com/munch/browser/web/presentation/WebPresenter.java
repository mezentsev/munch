package com.munch.browser.web.presentation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.helpers.ImageHelper;
import com.munch.browser.web.WebActivityContract;
import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;
import com.munch.webview.MunchWebView;
import com.munch.webview.WebProgressListener;

import javax.inject.Inject;

public class WebPresenter implements WebActivityContract.Presenter {
    private static final String TAG = "[MNCH:WebPresenter]";

    @NonNull
    private final HistoryDataSource mHistoryDataSource;
    @Nullable
    private MunchWebView mView;

    @Inject
    public WebPresenter(@NonNull HistoryDataSource historyDataSource) {
        mHistoryDataSource = historyDataSource;
        Log.d(TAG, "inited");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @Override
    public void attachView(@NonNull MunchWebView view) {
        mView = view;
        mView.setProgressListener(new ProgressListener(mHistoryDataSource));
        Log.d(TAG, "attach");
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
        Log.d(TAG, "detach");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroy");
    }

    @Override
    public void useUrl(@NonNull final String url) {
        if (mView != null) {
            String preparedUrl = prepareUrl(url);
            mView.loadUrl(preparedUrl);
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

    private static class ProgressListener implements WebProgressListener {
        private static final String TAG = "[MNCH:PL]";

        @NonNull
        private final HistoryDataSource mHistoryDataSource;
        @Nullable
        private History mHistory;

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

            if (mHistory != null) {
                String base64FromBitmap = ImageHelper.getBase64FromBitmap(favicon);
                if (base64FromBitmap != null) {
                    mHistory.setFavicon(base64FromBitmap);
                    mHistoryDataSource.saveHistory(mHistory);
                }
            }
        }

        @Override
        public void onFinish(long timestamp,
                             @NonNull String url,
                             @NonNull String title) {
            Log.d(TAG, "onFinish " + url);

            mHistory = new History(url, title, timestamp);
            mHistoryDataSource.saveHistory(mHistory);
        }

        @Override
        public void onError(long timestamp,
                            @NonNull String url,
                            int errorCode) {
            Log.d(TAG, "onError " + url);
        }
    }
}
