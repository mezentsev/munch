package com.munch.webview.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.munch.webview.R;

import dagger.android.support.DaggerFragment;

public class MunchWebFragment extends DaggerFragment {

    private static final String URI_KEY = "URI";

    @NonNull
    private MunchWebLayout mMunchWebLayout;
    @Nullable
    private String mUrl;

    @NonNull
    public static MunchWebFragment newInstance(@NonNull Uri url) {
        MunchWebFragment f = new MunchWebFragment();

        Bundle args = new Bundle();
        args.putString(URI_KEY, url.toString());
        f.setArguments(args);

        return f;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_webview, container, false);

        mMunchWebLayout = view.findViewById(R.id.munch_browser_webview);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle arguments = getArguments();
        if (arguments != null) {
            mUrl = arguments.getString(URI_KEY);
        }

        mMunchWebLayout.getWebView().openUrl(mUrl);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
