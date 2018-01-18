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

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class HistoryFragment extends DaggerFragment implements HistoryContract.View {
    private static final String TAG = "[MNCH:HistoryFragment]";

    @Inject
    Context mContext;

    @Inject
    HistoryContract.Presenter mHistoryPresenter;

    @NonNull
    private View mHistoryView;

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
    public void informHistoryLoad(int count) {
        Log.d(TAG, "informHistoryLoad");
        Snackbar.make(mHistoryView, "History count: " + count, Snackbar.LENGTH_SHORT).show();
    }
}
