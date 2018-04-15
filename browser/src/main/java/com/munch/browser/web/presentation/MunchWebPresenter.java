package com.munch.browser.web.presentation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.munch.browser.helpers.ImageHelper;
import com.munch.browser.web.MunchWebContract;
import com.munch.history.model.History;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FlowableRepository;
import com.munch.webview.WebContract;
import com.munch.webview.WebProgressListener;

import javax.inject.Inject;

@ActivityScoped
public class MunchWebPresenter implements MunchWebContract.Presenter, WebProgressListener {
    private static final String TAG = "[MNCH:WebPresenter]";

    @NonNull
    private final FlowableRepository<History> mHistoryRepository;
    @NonNull
    private final WebContract.View mWebView;

    @Nullable
    private MunchWebContract.View mView;
    @Nullable
    private String mUrl;

    @Nullable
    private History mHistory;

    @Inject
    public MunchWebPresenter(@NonNull WebContract.View webView,
                             @NonNull FlowableRepository<History> historyRepository) {
        mWebView = webView;
        mHistoryRepository = historyRepository;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onFirstAttachView() {
    }

    @Override
    public void attachView(@NonNull MunchWebContract.View view) {
        mView = view;

        mWebView.addProgressListener(this);
        mWebView.setScrollListener(new WebContract.ScrollListener() {
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
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    @Override
    public void goForward() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        }
    }

    @Override
    public void detachView() {
        mWebView.setScrollListener(null);
        mWebView.removeProgressListener(this);
        mView = null;
        Log.d(TAG, "detach");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "destroy");
    }

    @Override
    public void useUrl(@NonNull final String url) {
        mUrl = prepareUrl(url);
        mWebView.loadUrl(mUrl);
    }

    @Override
    public void reload() {
        mWebView.reload();
    }


    @Override
    public void onStart(long timestamp, @NonNull WebView view, @NonNull String url) {
        Log.d(TAG, "Started loading: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());

        if (!url.equals(mUrl)) {
            mUrl = url;
            mHistory = new History(url);
        }
    }

    @Override
    public void onFavicon(long timestamp, @NonNull WebView view, @Nullable Bitmap favicon) {
        Log.d(TAG, "onFavicon: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
        Log.d(TAG, "onFavicon 2: " + (favicon == null));

        if (mHistory != null && favicon != null) {
            // TODO: 13.04.18 another thread
            String fromBitmap = ImageHelper.getBase64FromBitmap(favicon);
            if (fromBitmap != null) {
                mHistory.setFavicon(fromBitmap);
                tryToSaveHistory();
            }
        }
    }

    @Override
    public void onScreenshot(long timestamp, @NonNull WebView view, @Nullable Bitmap screenshot) {
        Log.d(TAG, "onScreenshot: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
    }

    @Override
    public void onFinish(long timestamp, @NonNull WebView view, @NonNull String url) {
        Log.d(TAG, "onFinish: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
        mUrl = url;

        tryToSaveHistory();
        onEndLoading();
    }

    @Override
    public void onPageVisible(long timestamp, @NonNull WebView view, @NonNull String url) {
        Log.d(TAG, "onPageVisible: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
    }

    @Override
    public void onError(long timestamp, @NonNull WebView view, @NonNull String url, int errorCode) {
        Log.d(TAG, "onError: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
        onEndLoading();
    }

    @Override
    public void onTitle(long timestamp, @NonNull WebView view, @NonNull String title) {
        Log.d(TAG, "onTitle: " + (view.getFavicon() == null) + "; " + view.getTitle() + "; " + view.getUrl());
        if (mHistory != null) {
            mHistory.setTitle(title);
            tryToSaveHistory();
        }
    }

    @Override
    public void onProgressChanged(@NonNull WebView view, int progress) {
        changeProgress(progress);
    }

    private void tryToSaveHistory() {
        if (mHistory != null && mHistory.getTitle() != null && mHistory.getFavicon() != null) {
            mHistoryRepository.save(mHistory);
            Log.d(TAG,"History saved!");
        }
    }

    private void onEndLoading() {
        stopRefreshBySwipe();

        tryShowBackButton();
        tryShowForwardButton();
    }

    private void changeProgress(int progress) {
        if (mView != null) {
            mView.showProgress(progress);
        }
    }

    private boolean canGoBack() {
        return mWebView.canGoBack();
    }

    private boolean canGoForward() {
        return mWebView.canGoForward();
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
}
