package com.munch.browser.web.presentation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.helpers.ImageHelper;
import com.munch.browser.web.WebContract;
import com.munch.history.model.History;
import com.munch.history.model.HistoryDataSource;
import com.munch.webview.MunchWebContract;
import com.munch.webview.WebProgressListener;

import javax.inject.Inject;

public class WebPresenter implements WebContract.Presenter {
    private static final String TAG = "[MNCH:WebPresenter]";

    @NonNull
    private final HistoryDataSource mHistoryDataSource;
    @Nullable
    private MunchWebContract.View mWebView;
    private WebContract.View mView;

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
    public void attachView(@NonNull WebContract.View view) {
        mView = view;
    }

    @Override
    public void attachWebView(@NonNull MunchWebContract.View webView) {
        mWebView = webView;
        mWebView.setProgressListener(new ProgressListener(this, mHistoryDataSource));
        mWebView.setScrollListener(new MunchWebContract.ScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (mView != null) {
                    mView.enableRefreshBySwipe(l == 0);
                }
            }
        });

        Log.d(TAG, "attach");
    }

    @Override
    public void goBack() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    @Override
    public void goForward() {
        if (mWebView != null && mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }

    @Override
    public void detachView() {
        mWebView = null;
        mView = null;
        Log.d(TAG, "detach");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroy");
    }

    @Override
    public void useUrl(@NonNull final String url) {
        if (mWebView != null) {
            String preparedUrl = prepareUrl(url);
            mWebView.loadUrl(preparedUrl);
        }
    }

    private void changeProgress(int progress) {
        if (mView != null) {
            mView.showProgress(progress);
        }
    }

    private boolean canGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    private boolean canGoForward() {
        return mWebView != null && mWebView.canGoForward();
    }

    private void tryShowBackButton() {
        if (mView != null) {
            mView.showBackButton(canGoBack());
        }
    }

    private void tryShowForwardButton() {
        if (mView != null) {
            mView.showForwardButton(canGoForward());
        }
    }

    private void stopRefreshBySwipe() {
        if (mView != null) {
            mView.stopRefreshBySwipe();
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
        private final WebPresenter mPresenter;
        @NonNull
        private final HistoryDataSource mHistoryDataSource;

        @Nullable
        private History mHistory;

        ProgressListener(@NonNull final WebPresenter presenter,
                         @NonNull final HistoryDataSource historyDataSource) {
            mPresenter = presenter;
            mHistoryDataSource = historyDataSource;
        }

        @Override
        public void onStart(long timestamp,
                            @NonNull String url) {
        }

        @Override
        public void onFavicon(long timestamp,
                              @NonNull String url,
                              @NonNull Bitmap favicon) {
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

            mHistory = new History(url, title, timestamp);
            mHistoryDataSource.saveHistory(mHistory);

            onEnd();
        }

        @Override
        public void onError(long timestamp,
                            @NonNull String url,
                            int errorCode) {
            Log.e(TAG, timestamp + ": " + url + ", " + errorCode);

            onEnd();
        }

        @Override
        public void onProgressChanged(int progress) {
            mPresenter.changeProgress(progress);
            mPresenter.tryShowBackButton();
            mPresenter.tryShowForwardButton();
        }

        private void onEnd() {
            mPresenter.stopRefreshBySwipe();
            mPresenter.tryShowBackButton();
            mPresenter.tryShowForwardButton();
        }
    }
}
