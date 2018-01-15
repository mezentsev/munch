package com.munch.browser.main;

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
import com.munch.browser.suggest.SuggestActivity;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MainFragment extends DaggerFragment {
    private static final String TAG = "[MNCH:MainFragment]";

    @Nonnull
    private StaticOmniboxListener mOnClickCallback = () -> {
        Log.d(TAG, "onOmniboxClick");
        Intent intent = new Intent(getContext(), SuggestActivity.class);
        startActivity(intent);
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
        staticView.setOnClickListener(v -> {
            mOnClickCallback.onOmniboxClick();
        });

        return view;
    }
}
