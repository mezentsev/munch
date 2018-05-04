package com.munch.browser.bookmarks.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.munch.bookmarks.model.Bookmark;
import com.munch.browser.R;

import java.util.ArrayList;
import java.util.List;

final class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarkItemHolder> {
    @NonNull
    private List<Bookmark> mBookmarksList = new ArrayList<>();

    BookmarksAdapter() {
    }

    @Override
    public BookmarkItemHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater
                .from(context);

        return new BookmarkItemHolder(
                layoutInflater
                        .inflate(R.layout.munch_browser_bookmark_date_holder, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkItemHolder holder, int position) {
        holder.bind(mBookmarksList.get(position));
    }

    @Override
    public int getItemCount() {
        return mBookmarksList.size();
    }

    /**
     * Update bookmarks list in adapter.
     */
    public void setData(@NonNull List<Bookmark> bookmarkList) {
        mBookmarksList = bookmarkList;
        notifyDataSetChanged();
    }

    static class BookmarkItemHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView mTitleView;
        @NonNull
        private final TextView mDescriptionView;

        BookmarkItemHolder(@NonNull View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.munch_bookmark_title);
            mDescriptionView = itemView.findViewById(R.id.munch_bookmark_description);
        }

        void bind(@NonNull Bookmark bookmark) {
            String title = bookmark.getTitle();
            if (title != null) {
                mTitleView.setText(title.substring(0, 1));
                mDescriptionView.setText(title);
            }
        }
    }
}
