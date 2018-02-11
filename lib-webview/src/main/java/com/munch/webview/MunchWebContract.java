package com.munch.webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import com.munch.mvp.MvpContract;

public interface MunchWebContract extends MvpContract {
    interface View extends MvpContract.View {
        void setProgressBar(@Nullable ProgressBar progressBar);

        void openUrl(@NonNull String url);

        void loadHtml(@NonNull String html);

        void setProgressListener(@Nullable WebProgressListener progressListener);
    }

    interface WebProgressListener {
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
    }
}
