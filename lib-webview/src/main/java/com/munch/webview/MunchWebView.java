package com.munch.webview;

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

import java.io.File;

final class MunchWebView extends WebView implements MunchWebContract.View {

    private static final String TAG = "[MNCH:MunchWebView]";
    private static final String ERROR_WITH_DESCRIPTION = "<p style='line-height:400px; vertical-align: middle; text-align: center;'>%s</p>";
    private static final String NO_DATA = String.format(ERROR_WITH_DESCRIPTION, "MAIN MUNCH ERROR");

    @NonNull
    private final Context mContext;
    @Nullable
    private ProgressBar mProgressBar;
    @Nullable
    private MunchWebContract.WebProgressListener mProgressListener;
    @Nullable
    private String mTitle;
    @Nullable
    private String mUrl;

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
        setSaveEnabled(true);
        mContext = context;
        init();
    }

    @Override
    public void openUrl(@NonNull String url) {
        mUrl = prepareUrl(url);
        loadUrl(mUrl);
    }

    @Override
    public void loadHtml(@NonNull String html) {
        throw new IllegalStateException("Not implemented yet");
    }

    @Override
    public void setProgressBar(@Nullable ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    @Override
    public void setProgressListener(@Nullable MunchWebContract.WebProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    private void init() {
        mTitle = null;
        mUrl = null;

        final WebSettings webSettings = getSettings();

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setBlockNetworkImage(false);
        webSettings.setGeolocationEnabled(false);
        webSettings.setNeedInitialFocus(false);
        webSettings.setSaveFormData(true);
        webSettings.setAllowFileAccess(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //enableAppCache();

        setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view,
                                                  int progress) {
                        if (mProgressBar != null) {
                            mProgressBar.setProgress(progress);
                        }

                        Log.d(TAG, "Progress: " + progress);
                    }

                    @Override
                    public void onReceivedTitle(WebView view,
                                                String title) {
                        super.onReceivedTitle(view, title);
                        Log.d(TAG, "title received");

                        mTitle = title;
                    }

                    @Override
                    public void onReceivedIcon(WebView view,
                                               Bitmap icon) {
                        long timestamp = System.currentTimeMillis();

                        super.onReceivedIcon(view, icon);
                        Log.d(TAG, "icon received");

                        if (mProgressListener != null) {
                            mProgressListener.onFavicon(
                                    timestamp,
                                    mUrl,
                                    icon
                            );
                        }
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
            public void onPageFinished(@NonNull WebView view,
                                       @NonNull String url) {
                long timestamp = System.currentTimeMillis();
                webSettings.setLoadsImagesAutomatically(true);

                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }

                if (mProgressListener != null && mTitle != null) {
                    mProgressListener.onFinish(
                            timestamp,
                            mUrl,
                            mTitle);
                }

                Log.d(TAG, "Loading finished. Title: " +
                        mTitle + ". Url: " +
                        mUrl
                );
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
                                     String url) {
                long timestamp = System.currentTimeMillis();
                Log.e(TAG, "Error: " + errorCode + "; " + url + "; " + description);

                if (mOnLoadResourceCount <= 1) {
                    Log.d(TAG, "Internet connection error");
                    view.loadData(String.format(ERROR_WITH_DESCRIPTION, description), "text/html", "utf-8");

                    if (mProgressListener != null) {
                        mProgressListener.onError(
                                timestamp,
                                mUrl,
                                errorCode);
                    }
                }
            }
        });
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

    /**
     * Enable caching.
     * TODO: http://tutorials.jenkov.com/android/android-web-apps-using-android-webview.html#caching-web-resources-in-the-android-device
     */
    private void enableAppCache() {
        WebSettings webSettings = getSettings();

        webSettings.setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);

        File dir = mContext.getCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        webSettings.setAppCachePath(dir.getPath());
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }
}
