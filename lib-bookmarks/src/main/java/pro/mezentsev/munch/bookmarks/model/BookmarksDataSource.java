package pro.mezentsev.munch.bookmarks.model;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;

public interface BookmarksDataSource {
    /**
     * @return all history list
     */
    Flowable<List<Bookmark>> getBookmarksList();

    /**
     * Add new history to db.
     * @param history
     */
    void saveBookmark(@NonNull Bookmark history);

    /**
     * Remove history from database.
     * @param history
     */
    void removeBookmark(@NonNull Bookmark history);
}
