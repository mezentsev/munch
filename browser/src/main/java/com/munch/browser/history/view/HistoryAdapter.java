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
import com.munch.history.model.History;

import java.util.List;

import javax.annotation.Nullable;

final class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    @Nullable
    private List<History> mHistoryList;

    HistoryAdapter() {
    }

    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                            int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.munch_browser_history_holder, parent, false);
        return new HistoryHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder,
                                 int position) {
        holder.bind(mHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoryList != null
                ? mHistoryList.size()
                : 0;
    }

    public void setData(@Nullable List<History> historyList) {
        mHistoryList = historyList;
        notifyDataSetChanged();
    }

    static class HistoryHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final Context mContext;
        @NonNull
        private TextView mUrlView;
        @NonNull
        private TextView mTitleView;
        @NonNull
        private TextView mTimestampView;
        @NonNull
        private ImageView mFaviconView;

        public HistoryHolder(@NonNull Context context,
                             @NonNull View itemView) {
            super(itemView);
            mContext = context;

            mUrlView = itemView.findViewById(R.id.munch_history_url);
            mFaviconView = itemView.findViewById(R.id.munch_history_favicon);
            mTitleView = itemView.findViewById(R.id.munch_history_title);
            mTimestampView = itemView.findViewById(R.id.munch_history_timestamp);
        }

        public void bind(@NonNull History history) {
            mUrlView.setText(history.getUrl());

            // todo make real favicon
            Drawable drawable = mContext.getResources().getDrawable(android.R.drawable.ic_menu_recent_history);
            mFaviconView.setImageDrawable(drawable);

            mTitleView.setText(history.getTitle());
            mTimestampView.setText(history.getTime());
        }
    }
}
