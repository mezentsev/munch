package com.munch.webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

public interface WebProgressListener {
    void onStart(long timestamp,
                 @NonNull String url);

    void onFavicon(long timestamp,
                   @NonNull String url,
                   @NonNull Bitmap favicon);

    void onFinish(long timestamp,
                  @NonNull String url,
                  @NonNull String title);

    void onError(long timestamp,
                 @NonNull String url,
                 int errorCode);

    void onProgressChanged(int progress);
}
