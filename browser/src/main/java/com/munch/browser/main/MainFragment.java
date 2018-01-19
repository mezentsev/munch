package com.munch.browser.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.R;
import com.munch.browser.callbacks.StaticOmniboxListener;
import com.munch.browser.history.view.HistoryActivity;
import com.munch.browser.suggest.SuggestActivity;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment {
    private static final String TAG = "[MNCH:HistoryFragment]";

    @Inject
    Context mContext;

    @Nonnull
    private StaticOmniboxListener mOnClickCallback = new StaticOmniboxListener() {
        @Override
        public void onOmniboxClick() {
            Log.d(TAG, "onOmniboxClick");
            Intent intent = new Intent(mContext, SuggestActivity.class);
            MainFragment.this.startActivity(intent);
        }
    };

    @Inject
    public MainFragment() {

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_main_fragment, container, false);

        View staticView = view.findViewById(R.id.munch_static_omnibox_view);
        staticView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickCallback.onOmniboxClick();
            }
        });

        View showHistoryButton = view.findViewById(R.id.munch_show_history_button);
        showHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistoryActivity.class);
                MainFragment.this.startActivity(intent);
            }
        });

        setRetainInstance(true);

        return view;
    }
}
