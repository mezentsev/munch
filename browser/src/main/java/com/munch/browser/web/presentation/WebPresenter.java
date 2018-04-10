package com.munch.browser.web.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.munch.browser.web.WebContract;
import com.munch.history.HistoryRepository;
import com.munch.history.model.History;
import com.munch.webview.MunchWebContract;
import com.munch.webview.WebProgressListener;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WebPresenter implements WebContract.Presenter, WebProgressListener {
    private static final String TAG = "[MNCH:WebPresenter]";

    @NonNull
    private final HistoryRepository mHistoryRepository;
    @Nullable
    private MunchWebContract.View mWebView;
    @Nullable
    private WebContract.View mView;
    @Nullable
    private String mUrl;

    @Inject
    public WebPresenter(@NonNull HistoryRepository historyRepository) {
        mHistoryRepository = historyRepository;
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
        mWebView.setHistoryRepository(mHistoryRepository);
        mWebView.setProgressListener(this);
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
            mUrl = prepareUrl(url);
            mWebView.loadUrl(mUrl);
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

    @Override
    public void onStart(long timestamp, @NonNull String url) {
        Log.d(TAG, "Started loading " + url);
        mUrl = url;
    }

    @Override
    public void onFinish(long timestamp, @NonNull String url, @NonNull String title) {
        onEndLoading();
        mUrl = url;
    }

    @Override
    public void onError(long timestamp, @NonNull String url, int errorCode) {
        onEndLoading();
    }

    @Override
    public void onProgressChanged(int progress) {
        changeProgress(progress);

        tryShowBackButton();
        tryShowForwardButton();
    }

    private void onEndLoading() {
        stopRefreshBySwipe();

        tryShowBackButton();
        tryShowForwardButton();
    }
}
