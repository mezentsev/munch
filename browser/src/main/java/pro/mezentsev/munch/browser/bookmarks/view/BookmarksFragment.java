package pro.mezentsev.munch.browser.bookmarks.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pro.mezentsev.munch.bookmarks.model.Bookmark;
import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.base.view.BaseFragment;
import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;
import pro.mezentsev.munch.browser.web.view.MunchWebActivity;

import java.util.List;

import javax.inject.Inject;

public class BookmarksFragment extends BaseFragment
        implements BookmarksContract.View, BookmarksContract.BookmarkListener {
    private static final int SPAN_COUNT = 3;

    @Inject
    Context mContext;

    @Inject
    BookmarksContract.Presenter mBookmarksPresenter;

    @NonNull
    private RecyclerView mBookmarksView;
    @NonNull
    private BookmarksAdapter mBookmarksAdapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_recycler_fragment, container, false);
        mBookmarksView = view.findViewById(R.id.munch_recycler_view);

        mBookmarksAdapter = new BookmarksAdapter(this);

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(mContext, SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        mBookmarksView.setLayoutManager(linearLayoutManager);
        mBookmarksView.setAdapter(mBookmarksAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBookmarksPresenter.attachView(this);
        mBookmarksPresenter.loadAll();
    }

    @Override
    public void onPause() {
        mBookmarksPresenter.detachView();
        super.onPause();
    }

    @Override
    public void show(@NonNull List<Bookmark> bookmarkList) {
        setData(bookmarkList);
    }

    private void setData(@NonNull List<Bookmark> bookmarkList) {
        mBookmarksAdapter.setData(bookmarkList);
    }

    @Override
    public void onClick(@NonNull Bookmark bookmark) {
        Intent intent = new Intent(mContext, MunchWebActivity.class);
        intent.putExtra(MunchWebActivity.EXTRA_URI, bookmark.getUrl());
        mContext.startActivity(intent);
    }
}
