package pro.mezentsev.munch.browser.web.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MunchWebLayout extends SwipeRefreshLayout {

    @Nullable
    private WebView mWebView;

    public MunchWebLayout(Context context) {
        super(context);
    }

    public MunchWebLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void attachWebView(@NonNull WebView webView) {
        if (mWebView != null) {
            removeView(mWebView);
        }

        mWebView = webView;
        mWebView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mWebView);
    }

    public void detachWebView() {
        if (mWebView != null) {
            removeView(mWebView);
        }
    }
}
