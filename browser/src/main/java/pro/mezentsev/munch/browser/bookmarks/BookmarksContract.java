package pro.mezentsev.munch.browser.bookmarks;

import android.support.annotation.NonNull;

import pro.mezentsev.munch.bookmarks.model.Bookmark;
import pro.mezentsev.munch.mvp.MvpContract;

import java.util.List;

public interface BookmarksContract extends MvpContract {
    interface View extends MvpContract.View {
        void show(@NonNull List<Bookmark> bookmarkList);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadAll();
    }

    interface BookmarkListener {
        void onClick(@NonNull Bookmark bookmark);
    }
}
