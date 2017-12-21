package com.munch.webview.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

final class MunchWebView extends WebView {

    private static final String TAG = "[MNCH:MunchWebView]";
    private static final String NO_DATA = "<p style='line-height:400px; vertical-align: middle; text-align: center;'>MUNCH GENERAL ERROR! >:O</p>";

    @Nullable
    private ProgressBar mProgressBar;

    public MunchWebView(Context context) {
        this(context, null, 0);
    }

    public MunchWebView(Context context,
                        AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MunchWebView(Context context,
                        AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void init(@NonNull ProgressBar progressBar) {
        mProgressBar = progressBar;

        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Other webview options
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(true);
        //webSettings.setLoadWithOverviewMode(true);
        //webSettings.setUseWideViewPort(true);
        //webSettings.setBuiltInZoomControls(true);
        //setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        //setScrollbarFadingEnabled(false);

        setWebChromeClient(
                new WebChromeClient() {
                    public void onProgressChanged(WebView view,
                                                  int progress) {
                        mProgressBar.setProgress(progress);
                        Log.d(TAG, "Progress: " + progress);
                    }
                }
        );

        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "Loading started");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String url) {
                Log.d(TAG, "Loading " + url);

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view,
                                       String url) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "Loading finished");
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);

                Uri uri = request.getUrl();
                handleError(view, error.getErrorCode(), error.getDescription().toString(), uri);
            }

            @SuppressWarnings("deprecation")
            public void onReceivedError(WebView view,
                                        int errorCode,
                                        String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                Uri uri = Uri.parse(failingUrl);
                handleError(view, errorCode, description, uri);
            }

            private void handleError(@NonNull WebView view,
                                     int errorCode,
                                     String description,
                                     Uri uri) {
                Log.e(TAG, "Error: " + errorCode + "; " + uri + "; " + description);
                String text = "<html><body>" + "<p align=\"justify\">NO DATA</p>" + "</body></html>";
                view.loadData(NO_DATA, "text/html", "utf-8");
            }
        });
    }
}
