package pro.mezentsev.munch.bookmarks.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import pro.mezentsev.munch.bookmarks.model.Bookmark;

/**
 * The Room Database that contains the History table.
 */
@Database(entities = {Bookmark.class}, version = 1, exportSchema = false)
public abstract class BookmarksDatabase extends RoomDatabase {

    @NonNull
    public abstract BookmarksDao bookmarksDao();
}
