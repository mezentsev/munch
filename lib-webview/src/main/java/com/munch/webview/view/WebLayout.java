package com.munch.webview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.munch.webview.WebContract;
import com.munch.webview.R;

public final class WebLayout extends RelativeLayout {

    private static final String TAG = "[MNCH:MunchWebLayout]";

    @NonNull
    private WebContract.View mMunchWebView;
    @NonNull
    private ProgressBar mProgressBar;

    public WebLayout(@NonNull Context context) {
        this(context, null, 0);
    }

    public WebLayout(@NonNull Context context,
                     @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebLayout(@NonNull Context context,
                     @Nullable AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.munch_webview_layout, this);

        mMunchWebView = findViewById(R.id.munch_webview_munchwebview);
        mProgressBar = findViewById(R.id.munch_webview_progressbar);

        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mMunchWebView.setProgressBar(mProgressBar);
    }

    @NonNull
    public WebContract.View getWebView() {
        return mMunchWebView;
    }
}
