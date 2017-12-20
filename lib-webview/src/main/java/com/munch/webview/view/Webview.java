package com.munch.webview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public final class Webview extends WebView {
    public Webview(Context context) {
        this(context, null, 0);
    }

    public Webview(Context context,
                   AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Webview(Context context,
                   AttributeSet attrs,
                   int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
