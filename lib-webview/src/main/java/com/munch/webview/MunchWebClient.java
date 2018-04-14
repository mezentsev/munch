package com.munch.webview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;
import java.net.URISyntaxException;

public class MunchWebClient extends WebViewClient {

    private static final String ERROR_WITH_DESCRIPTION = "<html><title>Munch Error</title><body><p style='line-height:400px; vertical-align: middle; text-align: center;'>%s</p></body></html>";
    private static final String NO_DATA = String.format(ERROR_WITH_DESCRIPTION, "MAIN MUNCH ERROR");
    private static final String TAG = "[MunchWebClient]";

    @NonNull
    private final WebProgressListener mProgressListener;

    private int mOnLoadResourceCount = 0;

    public MunchWebClient(@NonNull WebProgressListener progressListener) {
        mProgressListener = progressListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view,
                                            String url) {
        Log.d(TAG, "Loading " + url);

        try {
            view.loadUrl(prepareUrl(url));
        } catch (URISyntaxException e) {
            Log.e(TAG, "Can't load", e);
        }

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
        view.getSettings().setLoadsImagesAutomatically(false);
        mOnLoadResourceCount = 0;

        mProgressListener.onStart(
                System.currentTimeMillis(),
                view,
                url);

        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(@NonNull WebView view,
                               @NonNull String url) {
        super.onPageFinished(view, url);

        mProgressListener.onFinish(
                System.currentTimeMillis(),
                view,
                view.getUrl());

        view.getSettings().setLoadsImagesAutomatically(true);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        mOnLoadResourceCount++;
    }

    @Override
    public void onPageCommitVisible(@NonNull WebView view,
                                    @NonNull String url) {
        super.onPageCommitVisible(view, url);
        mProgressListener.onPageVisible(System.currentTimeMillis(), view, url);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onReceivedError(WebView view,
                                WebResourceRequest request,
                                WebResourceError error) {
        super.onReceivedError(view, request, error);

        handleError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        Log.d(TAG, "doUpdate: " + url + " isReload: " + isReload);
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

            mProgressListener.onError(
                    timestamp,
                    view,
                    url,
                    errorCode);
        }
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
}
