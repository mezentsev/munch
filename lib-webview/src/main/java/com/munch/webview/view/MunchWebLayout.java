package com.munch.webview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.munch.webview.R;

public final class MunchWebLayout extends LinearLayout {

    private static final String TAG = "[MNCH:MunchWebLayout]";

    @NonNull
    private MunchWebView mMunchWebView;
    @NonNull
    private ProgressBar mProgressBar;

    public MunchWebLayout(Context context) {
        this(context, null, 0);
    }

    public MunchWebLayout(Context context,
                          @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MunchWebLayout(Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(VERTICAL);

        inflate(context, R.layout.munch_webview_layout, this);

        mMunchWebView = findViewById(R.id.munch_webview_munchwebview);
        mProgressBar = findViewById(R.id.munch_webview_progressbar);

        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        mMunchWebView.init(mProgressBar);
    }

    @NonNull
    public WebView getWebView() {
        return mMunchWebView;
    }
}