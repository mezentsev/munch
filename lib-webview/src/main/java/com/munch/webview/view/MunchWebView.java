package com.munch.webview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.munch.webview.WebContract;

import java.io.File;

import javax.inject.Inject;

public final class MunchWebView extends WebView implements WebContract.View {

    private static final String TAG = "[MNCH:MunchWebView]";
    @NonNull
    private final Context mContext;
    @Nullable
    private ProgressBar mProgressBar;

    @Inject
    WebContract.Presenter mWebPresenter;

    public MunchWebView(@NonNull Context context) {
        this(context, null, 0);
    }

    public MunchWebView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MunchWebView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSaveEnabled(true);
        mContext = context;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mWebPresenter.setProgressBar(mProgressBar);
        mWebPresenter.attachView(this);

        Log.d(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mWebPresenter.detachView();

        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    public void setProgressBar(@Nullable ProgressBar progressBar) {
        mProgressBar = progressBar;
        mWebPresenter.setProgressBar(mProgressBar);
    }

    @Override
    public void openUrl(@NonNull String url) {
        mWebPresenter.openUrl(url);
    }

    @Override
    public void prev() {
        mWebPresenter.prev();
    }

    @Override
    public void next() {
        mWebPresenter.next();
    }

    /**
     * Enable caching.
     * TODO: http://tutorials.jenkov.com/android/android-web-apps-using-android-webview.html#caching-web-resources-in-the-android-device
     */
    private void enableAppCache() {
        WebSettings webSettings = getSettings();

        webSettings.setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        webSettings.setAppCacheMaxSize(1024*1024*8);

        File dir = mContext.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        webSettings.setAppCachePath(dir.getPath());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }
}
