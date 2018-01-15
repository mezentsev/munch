package com.munch.webview.presentation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.munch.webview.WebContract;

import javax.inject.Inject;

public final class MunchWebPresenter implements WebContract.Presenter {

    private static final String ERROR_WITH_DESCRIPTION = "<p style='line-height:400px; vertical-align: middle; text-align: center;'>%s</p>";
    private static final String NO_DATA = String.format(ERROR_WITH_DESCRIPTION, "MAIN MUNCH ERROR");
    private static final String TAG = "[MNCH:WebPresenter]";

    @NonNull
    private final Context mContext;

    @Nullable
    private ProgressBar mProgressBar;

    @Nullable
    private WebView mWebView;

    public MunchWebPresenter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void setProgressBar(@Nullable ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    @Override
    public void openUrl(@NonNull String url) {
        if (mWebView != null) {
            mWebView.loadUrl(prepareUrl(url));
        } else {
            Log.e(TAG, "Not attached!");
        }
    }

    @Override
    public void prev() {

    }

    @Override
    public void next() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onFirstAttachView() {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void attachView(@NonNull WebContract.View view) {
        mWebView = (WebView) view;

        WebSettings webSettings = mWebView.getSettings();

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setBlockNetworkImage(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //enableAppCache();

        mWebView.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view,
                                                  int progress) {
                        if (mProgressBar != null) {
                            mProgressBar.setProgress(progress);
                        }

                        Log.d(TAG, "Progress: " + progress);
                    }
                }
        );

        mWebView.setWebViewClient(new WebViewClient() {
            private int mOnLoadResourceCount = 0;

            @Override
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                webSettings.setLoadsImagesAutomatically(false);
                mOnLoadResourceCount = 0;

                super.onPageStarted(view, url, favicon);

                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }

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
                String message = "SSL Certificate error." + error.getPrimaryError();
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
                message += " \"SSL Certificate Error\" Do you want to continue anyway?.. YES";

                Log.e(TAG, message);
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view,
                                       String url) {
                webSettings.setLoadsImagesAutomatically(true);

                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }

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

    @Override
    public void detachView() {
        mWebView = null;
        mProgressBar = null;
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {

    }

    /**
     * Normalizing url with http and lowercase.
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
