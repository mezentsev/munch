package com.munch.webview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import com.munch.mvp.MvpContract;

public interface MunchWebContract extends MvpContract {
    interface View extends MvpContract.View {
        void setProgressBar(@Nullable ProgressBar progressBar);

        void openUrl(@NonNull String url);

        void prev();

        void next();
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void setProgressBar(@Nullable ProgressBar progressBar);

        void openUrl(@NonNull String url);

        void prev();

        void next();
    }
}
