package com.munch.browser.history.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.R;
import com.munch.browser.history.HistoryContract;
import com.munch.history.model.History;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HistoryFragment extends DaggerFragment implements HistoryContract.View {
    @NonNull
    private static final String TAG = "[MNCH:HistoryFragment]";
    @NonNull
    private static final String NO_HISTORY = "No history";

    @Inject
    Context mContext;

    @Inject
    HistoryContract.Presenter mHistoryPresenter;

    @NonNull
    private HistoryView mHistoryView;

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
    public void informHistoryLoaded(@Nullable List<History> historyList) {
        if (historyList == null) {
            Snackbar.make(mHistoryView, NO_HISTORY, Snackbar.LENGTH_LONG).show();
        } else {
            int historyCount = historyList.size();
            String url = historyList.get(historyCount - 1).getUrl();
            String informText = "History count: " + historyCount + ". Last added: " + url;
            Snackbar.make(mHistoryView, informText, Snackbar.LENGTH_LONG).show();

            mHistoryView.setData(historyList);
        }
    }
}
