package com.munch.browser.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class NetworkExecutor implements Executor {
    @NonNull
    private final Executor mNetwork;

    @Inject
    public NetworkExecutor() {
        mNetwork = Executors.newCachedThreadPool();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mNetwork.execute(command);
    }
}
