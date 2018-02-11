package com.munch.browser.history.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.munch.history.model.History;

import java.util.List;

public class HistoryView extends RecyclerView {

    @NonNull
    private HistoryAdapter mHistoryAdapter;

    public HistoryView(Context context) {
        this(context, null);
    }

    public HistoryView(Context context,
                       @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryView(Context context,
                       @Nullable AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);

        mHistoryAdapter = new HistoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        setLayoutManager(linearLayoutManager);
        setAdapter(mHistoryAdapter);
    }

    public void setData(@Nullable List<History> historyList) {
        mHistoryAdapter.setData(historyList);
    }
}
