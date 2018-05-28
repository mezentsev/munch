package pro.mezentsev.munch.browser.bookmarks;

import android.support.annotation.NonNull;
import android.util.Log;

import pro.mezentsev.munch.bookmarks.model.Bookmark;
import pro.mezentsev.munch.bookmarks.model.BookmarksDataSource;
import pro.mezentsev.munch.mvp.FlowableRepository;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Implementation to load bookmarks from database.
 */
@Singleton
public final class BookmarksRepository implements FlowableRepository<Bookmark> {
    @NonNull
    private static final String TAG = "[MNCH:BookmarksRepo]";

    @NonNull
    private final Executor mExecutor;
    @NonNull
    private final BookmarksDataSource mLocalDataSource;

    @Inject
    public BookmarksRepository(@NonNull Executor executor,
                               @NonNull BookmarksDataSource localDataSource) {
        mExecutor = executor;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<List<Bookmark>> get() {
        return mLocalDataSource.getBookmarksList();
    }

    @Override
    public void save(@NonNull final Bookmark bookmark) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Saving bookmark: title=" + bookmark.getTitle() +
                        "; url=" + bookmark.getUrl() + "; isFavicon=" + (bookmark.getFavicon() != null));

                mLocalDataSource.saveBookmark(bookmark);
            }
        });
    }

    @Override
    public void remove(@NonNull final Bookmark bookmark) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Removing bookmark: title=" + bookmark.getTitle() +
                        "; url=" + bookmark.getUrl() + "; isFavicon=" + (bookmark.getFavicon() != null));

                mLocalDataSource.removeBookmark(bookmark);
            }
        });
    }
}
