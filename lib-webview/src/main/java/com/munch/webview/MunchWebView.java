package com.munch.webview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.munch.webview.MunchWebContract;
import com.munch.webview.MunchWebPresenter;

import java.io.File;

public final class MunchWebView extends WebView implements MunchWebContract.View {

    private static final String TAG = "[MNCH:MunchWebView]";
    @NonNull
    private final Context mContext;
    @Nullable
    private MunchWebContract.Presenter mWebPresenter;
    @Nullable
    private ProgressBar mProgressBar;

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

        if (mWebPresenter == null) {
            mWebPresenter = new MunchWebPresenter(context);
            mWebPresenter.onCreate();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mWebPresenter != null) {
            mWebPresenter.setProgressBar(mProgressBar);
            mWebPresenter.attachView(this);
        }

        Log.d(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mWebPresenter != null) {
            mWebPresenter.detachView();
        }

        Log.d(TAG, "onDetachedFromWindow");
    }

    @Override
    public void setProgressBar(@Nullable ProgressBar progressBar) {
        mProgressBar = progressBar;

        if (mWebPresenter != null) {
            mWebPresenter.setProgressBar(mProgressBar);
        }
    }

    @Override
    public void openUrl(@NonNull String url) {
        if (mWebPresenter != null) {
            mWebPresenter.openUrl(url);
        }
    }

    @Override
    public void prev() {
        if (mWebPresenter != null) {
            mWebPresenter.prev();
        }
    }

    @Override
    public void next() {
        if (mWebPresenter != null) {
            mWebPresenter.next();
        }
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
