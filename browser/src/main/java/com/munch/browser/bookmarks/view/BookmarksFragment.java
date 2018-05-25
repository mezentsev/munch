package com.munch.browser.bookmarks.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.bookmarks.model.Bookmark;
import com.munch.browser.R;
import com.munch.browser.base.view.BaseFragment;
import com.munch.browser.bookmarks.BookmarksContract;

import java.util.List;

import javax.inject.Inject;

public class BookmarksFragment extends BaseFragment implements BookmarksContract.View {
    @Inject
    Context mContext;

    @Inject
    BookmarksContract.Presenter mBookmarksPresenter;

    @NonNull
    private RecyclerView mBookmarksView;
    @NonNull
    private BookmarksAdapter mBookmarksAdapter;

    @Inject
    public BookmarksFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_recycler_fragment, container, false);
        mBookmarksView = view.findViewById(R.id.munch_recycler_view);

        mBookmarksAdapter = new BookmarksAdapter();

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        mBookmarksView.setLayoutManager(linearLayoutManager);
        mBookmarksView.setAdapter(mBookmarksAdapter);

        setRetainInstance(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mBookmarksPresenter.attachView(this);
        mBookmarksPresenter.loadBookmarks();
    }

    @Override
    public void onPause() {
        mBookmarksPresenter.detachView();
        super.onPause();
    }

    @Override
    public void showBookmarks(@NonNull List<Bookmark> bookmarkList) {
        setData(bookmarkList);
    }

    private void setData(@NonNull List<Bookmark> bookmarkList) {
        mBookmarksAdapter.setData(bookmarkList);
    }
}
