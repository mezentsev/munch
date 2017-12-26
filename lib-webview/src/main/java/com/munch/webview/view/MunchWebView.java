package com.munch.webview.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.lang.reflect.Method;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

final class MunchWebView extends WebView {

    private static final String TAG = "[MNCH:MunchWebView]";
    private static final String ERROR_WITH_DESCRIPTION = "<p style='line-height:400px; vertical-align: middle; text-align: center;'>%s</p>";
    private static final String NO_DATA = String.format(ERROR_WITH_DESCRIPTION, "MAIN MUNCH ERROR");

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

    @Override
    public void loadUrl(String url) {
        super.loadUrl(prepareUrl(url));
    }

    /**
     * Need to init firstly.
     *
     * @param progressBar progressBar view
     */
    @SuppressLint("SetJavaScriptEnabled")
    public void init(@NonNull ProgressBar progressBar) {
        setSaveEnabled(true);

        mProgressBar = progressBar;

        WebSettings webSettings = getSettings();

        // Other webview options
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view,
                                                  int progress) {
                        mProgressBar.setProgress(progress);
                        Log.d(TAG, "Progress: " + progress);
                    }
                }
        );

        setWebViewClient(new WebViewClient() {
            private int mOnLoadResourceCount = 0;

            @Override
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                webSettings.setLoadsImagesAutomatically(false);
                mOnLoadResourceCount = 0;

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
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += "\"SSL Certificate Error\" Do you want to continue anyway?.. YES";

                Log.e(TAG, message);
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view,
                                       String url) {
                webSettings.setLoadsImagesAutomatically(true);
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "Loading finished");
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                mOnLoadResourceCount++;
            }

            @Override
            @TargetApi(Build.VERSION_CODES.M)
            public void onReceivedError(WebView view,
                                        WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);

                handleError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
            }

            @SuppressWarnings("deprecation")
            public void onReceivedError(WebView view,
                                        int errorCode,
                                        String description,
                                        String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                handleError(view, errorCode, description, failingUrl);
            }

            private void handleError(@NonNull WebView view,
                                     int errorCode,
                                     String description,
                                     String uri) {
                Log.e(TAG, "Error: " + errorCode + "; " + uri + "; " + description);
                if (mOnLoadResourceCount <= 1) {
                    Log.d(TAG, "Internet connection error");
                    view.loadData(String.format(ERROR_WITH_DESCRIPTION, description), "text/html", "utf-8");
                }
            }
        });
    }

    /**
     * Normalizing url with http and lowecasing.
     *
     * @param url
     * @return normalized url
     */
    @NonNull
    private String prepareUrl(@NonNull String url) {
        String lowerUrl = url.toLowerCase();

        if (!lowerUrl.matches("^\\w+?://.*")) {
            lowerUrl = "http://" + lowerUrl;
        }

        return lowerUrl;
    }
}
