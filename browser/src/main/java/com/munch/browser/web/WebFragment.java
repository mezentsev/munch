package com.munch.browser.web;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.munch.browser.R;
import com.munch.webview.MunchWebContract;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WebFragment extends DaggerFragment {

    @NonNull
    private static String TAG = "[MNCH:WebFragment]";

    @Inject
    String mUri;

    @NonNull
    private MunchWebContract.View mMunchWebView;

    @Inject
    public WebFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_webview_layout, container, false);

        mMunchWebView = view.findViewById(com.munch.webview.R.id.munch_webview_munchwebview);
        ProgressBar mProgressBar = view.findViewById(com.munch.webview.R.id.munch_webview_progressbar);

        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mMunchWebView.setProgressBar(mProgressBar);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mMunchWebView.openUrl(mUri);
    }
}
