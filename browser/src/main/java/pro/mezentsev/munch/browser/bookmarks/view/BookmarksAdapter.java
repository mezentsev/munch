package pro.mezentsev.munch.browser.bookmarks.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pro.mezentsev.munch.bookmarks.model.Bookmark;
import pro.mezentsev.munch.browser.R;
import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;

import java.util.ArrayList;
import java.util.List;

import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;

final class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarkItemHolder> {
    @NonNull
    private final BookmarksContract.BookmarkListener mBookmarkListener;
    @NonNull
    private List<Bookmark> mBookmarksList = new ArrayList<>();

    BookmarksAdapter(@NonNull BookmarksContract.BookmarkListener bookmarkListener) {
        mBookmarkListener = bookmarkListener;
    }

    @Override
    public BookmarkItemHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater
                .from(context);

        return new BookmarkItemHolder(
                layoutInflater
                        .inflate(R.layout.munch_browser_bookmark_icon_view, parent, false),
                mBookmarkListener
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

    static class BookmarkItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @NonNull
        protected final BookmarksContract.BookmarkListener mBookmarkListener;
        @NonNull
        protected final TextView mTitleView;
        @NonNull
        protected final TextView mDescriptionView;

        @Nullable
        protected Bookmark mBookmark;

        BookmarkItemHolder(@NonNull View itemView,
                           @NonNull BookmarksContract.BookmarkListener bookmarkListener) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.munch_bookmark_title);
            mDescriptionView = itemView.findViewById(R.id.munch_bookmark_description);
            mBookmarkListener = bookmarkListener;

            itemView.setOnClickListener(this);
        }

        void bind(@NonNull Bookmark bookmark) {
            mBookmark = bookmark;

            String title = bookmark.getTitle();
            if (title != null) {
                mTitleView.setText(title.substring(0, 1));
                mDescriptionView.setText(title);
            } else {
                mTitleView.setText("");
                mDescriptionView.setText(R.string.munch_bookmark_unnamed_description);
            }
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mBookmark != null) {
                mBookmarkListener.onClick(mBookmark);
            }
        }
    }
}
