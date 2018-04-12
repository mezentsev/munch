package com.munch.browser.web.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.munch.browser.R;
import com.munch.browser.main.MainActivity;
import com.munch.browser.web.MunchWebContract;
import com.munch.webview.WebContract;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MunchWebActivity extends DaggerAppCompatActivity implements MunchWebContract.View {

    @NonNull
    public static final String EXTRA_URI = "URI";

    @Inject
    protected WebContract.View mWebView;

    @Inject
    protected MunchWebContract.Presenter mWebPresenter;

    @NonNull
    private ProgressBar mProgressBar;
    @NonNull
    private FloatingActionButton mForwardButton;
    @NonNull
    private FloatingActionButton mBackButton;
    @NonNull
    private MunchWebLayout mWebViewLayout;

    @Nullable
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_web_layout);

        mProgressBar = findViewById(R.id.munch_webview_progressbar);
        mWebViewLayout = findViewById(R.id.munch_webview_layout);
        mBackButton = findViewById(R.id.munch_webview_action_button_back);
        mForwardButton = findViewById(R.id.munch_webview_action_button_forward);

        mWebViewLayout.attachWebView(mWebView.obtainWebView());
        // TODO: 10.04.18 not here
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        mWebViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.reload();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebPresenter.goBack();
            }
        });

        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebPresenter.goForward();
            }
        });

        mWebPresenter.attachView(this);

        if (savedInstanceState == null) {
            mUrl = getIntent().getStringExtra(EXTRA_URI);
        } else {
            mUrl = "about:blank";
        }

        mWebView.loadUrl("https://yandex.ru");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewLayout.detachWebView();
        mWebPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showProgress(int progress) {
        if (progress == 0 || progress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        mProgressBar.setProgress(progress);
    }

    @Override
    public void showBackButton(final boolean isShow) {
        mBackButton.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mBackButton.setClickable(isShow);
    }

    @Override
    public void showForwardButton(boolean isShow) {
        mForwardButton.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mForwardButton.setEnabled(isShow);
    }

    @Override
    public void stopRefreshBySwipe() {
        mWebViewLayout.setRefreshing(false);
    }

    @Override
    public void enableRefreshBySwipe(boolean enable) {
        mWebViewLayout.setEnabled(enable);
    }

    private void showToast(@NonNull String toast) {
        Snackbar.make(mWebViewLayout, toast, Snackbar.LENGTH_SHORT);
    }
}
