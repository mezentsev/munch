package com.munch.browser.history.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.R;
import com.munch.browser.history.HistoryContract;
import com.munch.browser.web.view.WebActivity;
import com.munch.history.model.History;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HistoryFragment extends DaggerFragment implements HistoryContract.View {
    @NonNull
    private static final String TAG = "[MNCH:HistoryFragment]";
    @NonNull
    private static final String NO_HISTORY = "No History";

    @Inject
    Context mContext;

    @Inject
    HistoryContract.Presenter mHistoryPresenter;

    @NonNull
    private RecyclerView mHistoryView;
    @NonNull
    private HistoryAdapter mHistoryAdapter;

    @Inject
    public HistoryFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_history_fragment, container, false);
        mHistoryView = view.findViewById(R.id.munch_history_view);

        mHistoryAdapter = new HistoryAdapter();
        mHistoryAdapter.setHistoryListener(new HistoryListener(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        mHistoryView.setLayoutManager(linearLayoutManager);
        mHistoryView.setAdapter(mHistoryAdapter);

        setRetainInstance(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHistoryPresenter.attachView(this);

        showHistory();
    }

    @Override
    public void onPause() {
        mHistoryPresenter.detachView();
        super.onPause();
    }

    @Override
    public void showHistory() {
        Log.d(TAG, "showHistory");
        mHistoryPresenter.loadHistory();
    }

    @Override
    public void informHistoryLoaded(@NonNull List<History> historyList) {
        if (historyList.size() == 0) {
            Snackbar.make(mHistoryView, NO_HISTORY, Snackbar.LENGTH_LONG).show();
        } else {
            int historyCount = historyList.size();
            String url = historyList.get(0).getUrl();
            String informText = "Shown History count: " + historyCount + ". Last added: " + url;
            Snackbar.make(mHistoryView, informText, Snackbar.LENGTH_LONG).show();

            setData(historyList);
        }
    }

    private void setData(@Nullable List<History> historyList) {
        mHistoryAdapter.setData(historyList);
    }

    private static class HistoryListener implements HistoryContract.HistoryListener {

        @NonNull
        private final Context mContext;

        HistoryListener(@NonNull Context context) {
            mContext = context;
        }

        @Override
        public void onHistoryClicked(@NonNull History history) {
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra(WebActivity.EXTRA_URI, history.getUrl());
            mContext.startActivity(intent);
        }
    }
}
