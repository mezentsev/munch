package com.munch.browser.web.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseActivity;
import com.munch.browser.main.MainActivity;
import com.munch.browser.web.MunchWebContract;
import com.munch.webview.WebContract;

import javax.inject.Inject;

public class MunchWebActivity extends BaseActivity implements MunchWebContract.View {

    @NonNull
    public static final String EXTRA_URI = "URI";

    @Inject
    protected WebContract.View mWebView;

    @Inject
    protected MunchWebContract.Presenter mPresenter;

    @NonNull
    private ProgressBar mProgressBar;
    @NonNull
    private FloatingActionButton mForwardButton;
    @NonNull
    private FloatingActionButton mBackButton;
    @NonNull
    private MunchWebLayout mWebViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_web_layout);

        mProgressBar = findViewById(R.id.munch_webview_progressbar);
        mWebViewLayout = findViewById(R.id.munch_webview_layout);
        mBackButton = findViewById(R.id.munch_webview_action_button_back);
        mForwardButton = findViewById(R.id.munch_webview_action_button_forward);

        mPresenter.attachView(this);
        mWebViewLayout.attachWebView(mWebView.obtainWebView());

        mWebViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.reload();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.goBack();
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.goForward();
            }
        });

        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        String intentUrl = savedInstanceState != null
                ? "about:blank"
                : getIntent().getStringExtra(EXTRA_URI);

        mWebView.loadUrl(intentUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebViewLayout.detachWebView();
        mPresenter.detachView();
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
}
