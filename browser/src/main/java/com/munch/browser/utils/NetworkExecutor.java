package com.munch.browser.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class NetworkExecutor implements Executor {
    @NonNull
    private final Executor mNetwork;

    public NetworkExecutor() {
        mNetwork = Executors.newCachedThreadPool();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mNetwork.execute(command);
    }
}
