package com.munch.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class MunchWebView extends WebView
        implements WebContract.View, WebProgressListener {

    @NonNull
    private final Context mContext;

    @NonNull
    private List<WebProgressListener> mProgressListeners = new ArrayList<>();
    @Nullable
    private WebContract.ScrollListener mScrollListener;


    @Inject
    public MunchWebView(@NonNull Context context) {
        super(context);
        mContext = context;

        setSaveEnabled(true);
        init();
    }

    @NonNull
    @Override
    public WebView obtainWebView() {
        return this;
    }

    @Override
    public void setScrollListener(@Nullable WebContract.ScrollListener onScrollListener) {
        mScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Override
    public void addProgressListener(@NonNull WebProgressListener progressListener) {
        if (!mProgressListeners.contains(progressListener)) {
            mProgressListeners.add(progressListener);
        }
    }

    @Override
    public void removeProgressListener(@NonNull WebProgressListener progressListener) {
        mProgressListeners.remove(progressListener);
        Log.d("webView", "mProgressListeners: " + mProgressListeners.size());
    }

    // TODO: 12.04.18 move to presenter
    private void init() {
        final WebSettings webSettings = getSettings();

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setBlockNetworkImage(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDomStorageEnabled(true);

        // http://tutorials.jenkov.com/android/android-web-apps-using-android-webview.html#caching-web-resources-in-the-android-device
        // Set cache size to 8 mb by default. should be more than enough
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);

        File dir = mContext.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        webSettings.setAppCachePath(dir.getPath());
        webSettings.setAppCacheEnabled(true);
        setVerticalScrollBarEnabled(true);

        setWebChromeClient(new MunchChromeWebClient(this));
        setWebViewClient(new MunchWebClient(this));
    }

    @Override
    public void destroy() {
        mProgressListeners.clear();
        mScrollListener = null;

        // Detaching this View is probably a good idea.
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }

        // Some possible hacks to avoid leaks.
        setWebChromeClient(null);
        setWebViewClient(null);
        loadUrl("about:blank");
        stopLoading();
        setVisibility(View.GONE);
        removeAllViews();
        destroyDrawingCache();

        super.destroy();
    }

    @Override
    public void onStart(long timestamp, @NonNull WebView webView, @NonNull String url) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onStart(timestamp, webView, url);
        }
    }

    @Override
    public void onFavicon(long timestamp, @NonNull WebView webView, @Nullable Bitmap favicon) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onFavicon(timestamp, webView, favicon);
        }
    }

    @Override
    public void onScreenshot(long timestamp, @NonNull WebView webView, @Nullable Bitmap screenshot) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onScreenshot(timestamp, webView, screenshot);
        }
    }

    @Override
    public void onFinish(long timestamp, @NonNull WebView webView, @NonNull String url) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onFinish(timestamp, webView, url);
        }
    }

    @Override
    public void onPageVisible(long timestamp, @NonNull WebView webView, @NonNull String url) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onPageVisible(timestamp, webView, url);
        }
    }

    @Override
    public void onError(long timestamp, @NonNull WebView webView, @NonNull String url, int errorCode) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onError(timestamp, webView, url, errorCode);
        }
    }

    @Override
    public void onTitle(long timestamp, @NonNull WebView webView, @NonNull String title) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onTitle(timestamp, webView, title);
        }
    }

    @Override
    public void onProgressChanged(@NonNull WebView webView, int progress) {
        for (WebProgressListener progressListener : mProgressListeners) {
            progressListener.onProgressChanged(webView, progress);
        }
    }
}
