package com.munch.browser.history.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.munch_browser_history_holder, parent, false);
        return new HistoryHolder(view);
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
        private TextView mUrlView;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            mUrlView = itemView.findViewById(R.id.munch_history_url);
        }

        public void bind(@NonNull History history) {
            mUrlView.setText(history.getUrl());
        }
    }
}
