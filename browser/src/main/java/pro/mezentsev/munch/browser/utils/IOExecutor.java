package pro.mezentsev.munch.browser.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class IOExecutor implements Executor {
    @NonNull
    private final Executor mDiskIO;

    @Inject
    public IOExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mDiskIO.execute(command);
    }
}
