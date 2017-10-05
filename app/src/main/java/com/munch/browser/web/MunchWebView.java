package com.munch.browser.web;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MunchWebView extends WebView implements IMunchWebContract.View {
    private MunchWebPresenter mMunchWebPresenter;

    public MunchWebView(@NonNull Context context) {
        this(context, null, 0);
    }

    public MunchWebView(@NonNull Context context,
                        @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MunchWebView(@NonNull Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (mMunchWebPresenter == null) {
            mMunchWebPresenter = new MunchWebPresenter();
        }

        mMunchWebPresenter.attach(this);
    }

    @Override
    public void loadUrl(@NonNull String url) {
        mMunchWebPresenter.loadUrl(url);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mMunchWebPresenter == null) {
            mMunchWebPresenter = new MunchWebPresenter();
        }

        mMunchWebPresenter.attach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mMunchWebPresenter = null;
    }
}
