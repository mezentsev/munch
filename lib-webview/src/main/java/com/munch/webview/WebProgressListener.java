package com.munch.webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface WebProgressListener {
    void onStart(long timestamp,
                 @NonNull String url);

    void onFavicon(long timestamp,
                   @NonNull String url,
                   @Nullable Bitmap favicon);

    void onScreenshot(long timestamp,
                      @NonNull String url,
                      @Nullable Bitmap screenshot);

    void onFinish(long timestamp,
                  @NonNull String url,
                  @NonNull String title);

    void onError(long timestamp,
                 @NonNull String url,
                 int errorCode);

    void onProgressChanged(int progress);
}
