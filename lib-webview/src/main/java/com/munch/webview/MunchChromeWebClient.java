package com.munch.webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MunchChromeWebClient extends WebChromeClient {

    @NonNull
    private final WebProgressListener mProgressListener;

    public MunchChromeWebClient(@NonNull WebProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        mProgressListener.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        mProgressListener.onTitle(System.currentTimeMillis(), view, title);
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
        mProgressListener.onFavicon(System.currentTimeMillis(), view, icon);
    }
}
