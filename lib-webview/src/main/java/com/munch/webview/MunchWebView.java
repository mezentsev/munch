package com.munch.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.munch.history.HistoryRepository;
import com.munch.history.model.History;
import com.munch.webview.helpers.ImageHelper;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.inject.Singleton;

public final class MunchWebView extends WebView implements MunchWebContract.View {

    private static final String TAG = "[MNCH:MunchWebView]";
    private static final String ERROR_WITH_DESCRIPTION = "<html><title>Munch Error</title><body><p style='line-height:400px; vertical-align: middle; text-align: center;'>%s</p></body></html>";
    private static final String NO_DATA = String.format(ERROR_WITH_DESCRIPTION, "MAIN MUNCH ERROR");

    @NonNull
    private final Context mContext;

    @Nullable
    private WebProgressListener mProgressListener;
    @Nullable
    private String mTitle;
    @Nullable
    private MunchWebContract.WebArchiveListener mWebArchiveListener;
    @Nullable
    private MunchWebContract.ScrollListener mScrollListener;
    @Nullable
    private History mHistory;
    @Nullable
    private HistoryRepository mHistoryRepository;

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
    public void setScrollListener(@NonNull MunchWebContract.ScrollListener onScrollListener) {
        mScrollListener = onScrollListener;
    }

    @Override
    public void loadUrl(@NonNull String url) {
        // TODO: 10.04.18 get url from bundle
        try {
            url = prepareUrl(url);
        } catch (URISyntaxException e) {
            return;
        }

        super.loadUrl(url);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mScrollListener != null) {
            mScrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("history", mHistory);
        bundle.putParcelable("super", super.onSaveInstanceState());
        // TODO: 10.04.18  
        //saveState(bundle);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        mHistory = bundle.getParcelable("history");
        // TODO: 10.04.18 load url on restore
        //restoreState(bundle);
        super.onRestoreInstanceState(bundle.getParcelable("super"));
    }

    @Override
    public void setProgressListener(@Nullable WebProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public void setWebArchiveListener(@NonNull MunchWebContract.WebArchiveListener webArchiveListener) {
        mWebArchiveListener = webArchiveListener;
    }

    @Override
    public void setHistoryRepository(@NonNull HistoryRepository historyRepository) {
        mHistoryRepository = historyRepository;
    }

    private void init() {
        mTitle = null;

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

        setVerticalScrollBarEnabled(true);

        //enableAppCache();

        setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view,
                                                  int progress) {
                        if (mProgressListener != null) {
                            mProgressListener.onProgressChanged(progress);
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
                        super.onReceivedIcon(view, icon);
                        Log.d(TAG, "icon received for url: " + view.getUrl());

                        // TODO: 10.04.18
                        if (mHistory != null && mHistoryRepository != null) {
                            String base64FromBitmap = ImageHelper.getBase64FromBitmap(icon);
                            if (base64FromBitmap != null) {
                                mHistory.setFavicon(base64FromBitmap);
                                mHistoryRepository.saveHistory(mHistory);
                            }
                        }
                    }
                }
        );

        setWebViewClient(new WebViewClient() {
            private int mOnLoadResourceCount = 0;

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
            public void onPageStarted(WebView view,
                                      String url,
                                      Bitmap favicon) {
                webSettings.setLoadsImagesAutomatically(false);
                mOnLoadResourceCount = 0;

                if (mHistory != null && mHistoryRepository != null) {
                    mHistory.setUrl(url);
                    mHistoryRepository.saveHistory(mHistory);
                }

                super.onPageStarted(view, url, favicon);

                Log.d(TAG, "Loading started " + url);
            }

            @Override
            public void onPageFinished(@NonNull WebView view,
                                       @NonNull String url) {
                webSettings.setLoadsImagesAutomatically(true);

                // TODO: 10.04.18
                if (mTitle != null && !mTitle.equals("Munch Error")) {
                    long timestamp = System.currentTimeMillis();

                    if (mHistory == null) {
                        mHistory = new History(timestamp, url, mTitle);
                    }

                    if (mHistoryRepository != null) {
                        mHistoryRepository.saveHistory(mHistory);
                    }

                    if (mProgressListener != null) {
                        mProgressListener.onFinish(
                                timestamp,
                                url,
                                mTitle);
                    }
                }

                Log.d(TAG, "Loading finished. Title: " +
                        mTitle + ". Url: " +
                        url
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
                                url,
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
    private String prepareUrl(@NonNull String url) throws URISyntaxException {
        String lowerUrl = url.toLowerCase();

        if (!lowerUrl.matches("^\\w+?://.*")) {
            lowerUrl = "http://" + lowerUrl;
        }

        int pos = lowerUrl.lastIndexOf('/') + 1;

        URI uri = new URI(lowerUrl.substring(0, pos) + Uri.encode(lowerUrl.substring(pos)));
        return url;
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
