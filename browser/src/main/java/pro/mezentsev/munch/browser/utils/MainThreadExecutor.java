package pro.mezentsev.munch.browser.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainThreadExecutor implements Executor {
    @NonNull
    private Handler mMainThreadHandler;

    @Inject
    public MainThreadExecutor() {
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mMainThreadHandler.post(command);
    }
}