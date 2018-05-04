package com.munch.browser.bookmarks;

import android.support.annotation.NonNull;

import com.munch.bookmarks.model.Bookmark;
import com.munch.mvp.MvpContract;

import java.util.List;

public interface BookmarksContract extends MvpContract {
    interface View extends MvpContract.View {
        void showBookmarks(@NonNull List<Bookmark> bookmarkList);
    }

    interface Presenter extends MvpContract.Presenter<View> {
        void loadBookmarks();
    }
}
