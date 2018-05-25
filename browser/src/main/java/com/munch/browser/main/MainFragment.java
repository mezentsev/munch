package com.munch.browser.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseFragment;
import com.munch.browser.callbacks.StaticOmniboxListener;
import com.munch.browser.history.view.HistoryActivity;
import com.munch.browser.suggest.view.SuggestActivity;

import javax.inject.Inject;

public class MainFragment extends BaseFragment {
    @Inject
    Context mContext;

    @NonNull
    private StaticOmniboxListener mOnClickCallback = new StaticOmniboxListener() {
        @Override
        public void onOmniboxClick() {
            Intent intent = new Intent(mContext, SuggestActivity.class);
            startActivity(intent);
        }
    };

    @Inject
    public MainFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);
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
                startActivity(intent);
            }
        });

        return view;
    }
}
