package com.munch.webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebView;

public interface WebProgressListener {
    void onStart(long timestamp,
                 @NonNull WebView view,
                 @NonNull String url);

    void onFavicon(long timestamp,
                   @NonNull WebView view,
                   @Nullable Bitmap favicon);

    void onScreenshot(long timestamp,
                      @NonNull WebView view,
                      @Nullable Bitmap screenshot);

    void onFinish(long timestamp,
                  @NonNull WebView view,
                  @NonNull String url);

    void onPageVisible(long timestamp,
                       @NonNull WebView view,
                       @NonNull String url);

    void onError(long timestamp,
                 @NonNull WebView view,
                 @NonNull String url,
                 int errorCode);

    void onTitle(long timestamp,
                 @NonNull WebView view,
                 @NonNull String title);

    void onProgressChanged(@NonNull WebView view,
                           int progress);
}
