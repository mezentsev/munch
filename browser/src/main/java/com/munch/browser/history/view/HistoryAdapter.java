package com.munch.browser.history.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.munch.browser.R;
import com.munch.browser.helpers.DateHelper;
import com.munch.browser.helpers.ImageHelper;
import com.munch.browser.history.HistoryContract;
import com.munch.history.model.History;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

final class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HistoryAdapterListener {
    protected static final int DATE_HOLDER = 0;
    protected static final int HISTORY_ITEM_HOLDER = 1;

    @Nullable
    private HistoryContract.HistoryListener mHistoryListener;
    @NonNull
    private List<History> mHistoryList = new ArrayList<>();

    HistoryAdapter() {
    }

    /**
     * Set listener to operate with history items.
     *
     * @param historyListener
     */
    public void setHistoryListener(@Nullable HistoryContract.HistoryListener historyListener) {
        mHistoryListener = historyListener;
    }

    /**
     * Remove history item from adapter.
     *
     * @param position
     */
    public void removeItem(@IntRange(from = 0) int position) {
        if (position >= 0) {
            History history = mHistoryList.remove(position);
            notifyItemRemoved(position);

            if (mHistoryListener != null) {
                mHistoryListener.onHistoryRemoved(history);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater
                .from(context);

        switch (viewType) {
            case DATE_HOLDER:
                return new HistoryDateHolder(
                        context,
                        layoutInflater
                                .inflate(R.layout.munch_browser_history_date_holder, parent, false)
                );
            case HISTORY_ITEM_HOLDER:
                return new HistoryItemHolder(
                        context,
                        layoutInflater
                                .inflate(R.layout.munch_browser_history_holder, parent, false),
                        this
                );
            default:
                throw new IllegalStateException("Not supported");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {
        ((HistoryItemHolder) holder).bind(mHistoryList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return HISTORY_ITEM_HOLDER;
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    /**
     * Update history list in adapter.
     */
    public void setData(@NonNull List<History> historyList) {
        mHistoryList = historyList;
        notifyDataSetChanged();
    }

    @Override
    public void onClicked(int position) {
        if (mHistoryListener != null && position >= 0) {
            mHistoryListener.onHistoryClicked(mHistoryList.get(position));
        }
    }

    static class HistoryDateHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final TextView mDateView;

        HistoryDateHolder(@NonNull Context context,
                          @NonNull View itemView) {
            super(itemView);

            mDateView = itemView.findViewById(R.id.munch_bookmark_item);
        }

        void bind(@NonNull String date) {
            mDateView.setText(date);
        }
    }

    static class HistoryItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @NonNull
        private final Context mContext;
        @NonNull
        private final TextView mUrlView;
        @NonNull
        private final TextView mTitleView;
        @NonNull
        private final TextView mTimestampView;
        @NonNull
        private final ImageView mFaviconView;
        @Nullable
        private final HistoryAdapterListener mHistoryClickListener;

        HistoryItemHolder(@NonNull Context context,
                          @NonNull View itemView,
                          @Nullable HistoryAdapterListener historyListener) {
            super(itemView);
            mContext = context;

            mUrlView = itemView.findViewById(R.id.munch_history_url);
            mFaviconView = itemView.findViewById(R.id.munch_history_favicon);
            mTitleView = itemView.findViewById(R.id.munch_history_title);
            mTimestampView = itemView.findViewById(R.id.munch_history_timestamp);
            mHistoryClickListener = historyListener;

            itemView.setOnClickListener(this);
        }

        void bind(@NonNull History history) {
            mUrlView.setText(history.getUrl());

            String favicon64 = history.getFavicon();
            if (favicon64 == null) {
                Drawable drawable = mContext.getResources().getDrawable(android.R.drawable.ic_menu_recent_history);
                mFaviconView.setImageDrawable(drawable);
            } else {
                // todo need to do it not in UI thread
                mFaviconView.setImageBitmap(ImageHelper.getBitmapFromBase64(favicon64));
            }

            mTitleView.setText(history.getTitle());
            mTimestampView.setText(DateHelper.getTime(history.getTimestamp()));
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mHistoryClickListener != null) {
                mHistoryClickListener.onClicked(getAdapterPosition());
            }
        }
    }
}
