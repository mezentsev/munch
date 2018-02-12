package com.munch.browser.history.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.munch.browser.R;
import com.munch.browser.helpers.ImageHelper;
import com.munch.browser.history.HistoryContract;
import com.munch.history.model.History;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

final class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HistoryAdapterListener {
    private static final int DATE_HOLDER = 0;
    private static final int HISTORY_ITEM_HOLDER = 1;

    @Nullable
    private HistoryContract.HistoryListener mHistoryListener;
    @Nullable
    private List<History> mHistoryList;
    @Nullable
    private List<String> mDateList;
    @Nullable
    private List<Integer> mIndexer;
    private int mDateSize = 0;
    private int mHistorySize = 0;

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
        Integer dateIndex = mIndexer.get(position);
        if (dateIndex >= mHistorySize) {
            ((HistoryDateHolder) holder).bind(mDateList.get(dateIndex - mHistorySize));
        } else {
            ((HistoryItemHolder) holder).bind(mHistoryList.get(dateIndex));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Integer dateIndex = mIndexer.get(position);

        return dateIndex >= mHistorySize
                ? DATE_HOLDER
                : HISTORY_ITEM_HOLDER;
    }

    @Override
    public int getItemCount() {
        return mHistorySize + mDateSize;
    }

    public void setData(@Nullable List<History> historyList) {
        mHistoryList = historyList;

        if (historyList != null) {
            mHistorySize = historyList.size();
            mIndexer = new ArrayList<>(2 * mHistorySize);
            mDateList = new ArrayList<>();

            String lastHistoryDate = null;
            int lastDateId = 0;
            for (int i = 0; i < mHistorySize; ++i) {
                History curHistory = historyList.get(i);
                String curHistoryDate = curHistory.getDate();

                if (lastHistoryDate != null && lastHistoryDate.equals(curHistoryDate)) {
                    // set history holder
                    mIndexer.add(i);
                } else {
                    mIndexer.add(mHistorySize + lastDateId);
                    mIndexer.add(i);
                    mDateList.add(curHistoryDate);
                    lastDateId++;
                }

                lastHistoryDate = curHistoryDate;
            }

            mDateSize = mDateList.size();
        } else {
            mDateList = null;
            mIndexer = null;
            mHistorySize = 0;
            mDateSize = 0;
        }

        notifyDataSetChanged();
    }

    @Override
    public void onClicked(int adapterPosition) {
        if (mHistoryListener != null) {
            Integer index = mIndexer.get(adapterPosition);
            if (index >= mHistorySize) {
                return;
            } else {
                mHistoryListener.onHistoryClicked(mHistoryList.get(index));
            }
        }
    }

    static class HistoryDateHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final Context mContext;
        @NonNull
        private final TextView mDateView;

        HistoryDateHolder(@NonNull Context context,
                          @NonNull View itemView) {
            super(itemView);

            mContext = context;
            mDateView = itemView.findViewById(R.id.munch_history_date);
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
        @Nullable
        private final HistoryAdapterListener mHistoryClickListener;
        @NonNull
        private final ImageView mFaviconView;

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
            mTimestampView.setText(history.getTime());
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mHistoryClickListener != null) {
                mHistoryClickListener.onClicked(getAdapterPosition());
            }
        }
    }
}
