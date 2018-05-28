package pro.mezentsev.munch.browser.history;

import android.support.annotation.NonNull;
import android.util.Log;

import pro.mezentsev.munch.history.model.History;
import pro.mezentsev.munch.history.model.HistoryDataSource;
import pro.mezentsev.munch.mvp.FlowableRepository;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Implementation to load history from database.
 */
@Singleton
public final class HistoryRepository implements FlowableRepository<History> {
    @NonNull
    private final Executor mExecutor;
    @NonNull
    private final HistoryDataSource mLocalDataSource;
    @NonNull
    private static final String TAG = "[MNCH:HistoryRepoImpl]";

    @Inject
    public HistoryRepository(@NonNull Executor executor,
                             @NonNull HistoryDataSource localDataSource) {
        mExecutor = executor;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<List<History>> get() {
        return mLocalDataSource.getHistoryList();
    }

    @Override
    public void save(@NonNull final History history) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Saving history: title=" + history.getTitle() +
                        "; url=" + history.getUrl() + "; isFavicon=" + (history.getFavicon() != null));

                mLocalDataSource.saveHistory(history);
            }
        });
    }

    @Override
    public void remove(@NonNull final History history) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Removing history: title=" + history.getTitle() +
                        "; url=" + history.getUrl() + "; isFavicon=" + (history.getFavicon() != null));

                mLocalDataSource.removeHistory(history);
            }
        });
    }
}
