package com.munch.browser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.browser.callbacks.StaticOmniboxCallback;

public class MainFragment extends Fragment {

    @NonNull
    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Nullable
    private StaticOmniboxCallback mOnClickCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnClickCallback = (StaticOmniboxCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement StaticOmniboxCallback");
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_main_fragment, container, false);
        View staticView = view.findViewById(R.id.munch_static_omnibox_view);
        staticView.setOnClickListener(v -> {
            if (mOnClickCallback != null) {
                mOnClickCallback.onStaticOmniboxClick();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickCallback = null;
    }
}
