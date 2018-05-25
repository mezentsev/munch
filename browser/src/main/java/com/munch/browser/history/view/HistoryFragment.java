package com.munch.browser.history.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseFragment;
import com.munch.browser.history.HistoryContract;
import com.munch.browser.web.view.MunchWebActivity;
import com.munch.history.model.History;

import java.util.List;

import javax.inject.Inject;

public class HistoryFragment extends BaseFragment implements HistoryContract.View {
    @NonNull
    private static final String TAG = "[MNCH:BookFragment]";
    @NonNull
    private static final String NO_HISTORY = "No History";

    @Inject
    HistoryContract.Presenter mHistoryPresenter;

    @NonNull
    private RecyclerView mHistoryView;
    @NonNull
    private HistoryAdapter mHistoryAdapter;
    @NonNull
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_recycler_fragment, container, false);
        mHistoryView = view.findViewById(R.id.munch_recycler_view);

        mHistoryAdapter = new HistoryAdapter();
        mHistoryAdapter.setHistoryListener(new HistoryListener(mContext));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        mHistoryView.setLayoutManager(linearLayoutManager);
        mHistoryView.setAdapter(mHistoryAdapter);
        mHistoryView.setHasFixedSize(false);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new BaseSwipeCallback(mContext, 0, ItemTouchHelper.LEFT, HistoryAdapter.DATE_HOLDER) {
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        mHistoryAdapter.removeItem(viewHolder.getAdapterPosition());
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(mHistoryView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHistoryPresenter.attachView(this);
        mHistoryPresenter.loadAll();
    }

    @Override
    public void onPause() {
        mHistoryPresenter.detachView();
        super.onPause();
    }

    @Override
    public void show(@NonNull List<History> historyList) {
        if (historyList.size() == 0) {
            //Snackbar.make(mHistoryView, NO_HISTORY, Snackbar.LENGTH_LONG).show();
        } else {
            //int historyCount = historyList.size();
            //String url = historyList.get(0).getUrl();
            //String informText = "Shown History count: " + historyCount + ". Last added: " + url;
            //Snackbar.make(mHistoryView, informText, Snackbar.LENGTH_LONG).show();

            setData(historyList);
        }
    }

    private void setData(@NonNull List<History> historyList) {
        mHistoryAdapter.setData(historyList);
    }

    private class HistoryListener implements HistoryContract.HistoryListener {

        @NonNull
        private final Context mContext;

        HistoryListener(@NonNull Context context) {
            mContext = context;
        }

        @Override
        public void onClicked(@NonNull History history) {
            Intent intent = new Intent(mContext, MunchWebActivity.class);
            intent.putExtra(MunchWebActivity.EXTRA_URI, history.getUrl());
            mContext.startActivity(intent);
        }

        @Override
        public void onRemoved(@NonNull History history) {
            Log.d(TAG, "remove");
            mHistoryPresenter.remove(history);
        }
    }
}
