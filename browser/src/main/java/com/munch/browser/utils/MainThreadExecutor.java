package com.munch.browser.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    @NonNull
    private Handler mMainThreadHandler;

    public MainThreadExecutor() {
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mMainThreadHandler.post(command);
    }
}