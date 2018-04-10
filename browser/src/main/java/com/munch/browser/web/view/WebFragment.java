package com.munch.browser.web.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.munch.browser.R;
import com.munch.browser.web.WebContract;
import com.munch.webview.MunchWebContract;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class WebFragment extends DaggerFragment implements WebContract.View {

    @NonNull
    private static String TAG = "[MNCH:WebFragment]";

    @Inject
    String mUri;

    @Inject
    WebContract.Presenter mPresenter;

    @NonNull
    private MunchWebContract.View mMunchWebView;
    @NonNull
    private ProgressBar mProgressBar;
    @NonNull
    private FloatingActionButton mForwardButton;
    @NonNull
    private FloatingActionButton mBackButton;
    @NonNull
    private SwipeRefreshLayout mRefreshLayout;

    @Inject
    public WebFragment() {
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.munch_browser_web_layout, container, false);

        mRefreshLayout = view.findViewById(R.id.munch_webview_refresh);
        mMunchWebView = view.findViewById(R.id.munch_webview_munchwebview);
        mProgressBar = view.findViewById(R.id.munch_webview_progressbar);
        mBackButton = view.findViewById(R.id.munch_webview_action_button_back);
        mForwardButton = view.findViewById(R.id.munch_webview_action_button_forward);

        // TODO: 10.04.18 not here
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMunchWebView.reload();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("back!");
                mPresenter.goBack();
            }
        });

        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("forward!");
                mPresenter.goForward();
            }
        });

        mPresenter.attachView(this);
        mPresenter.attachWebView(mMunchWebView);

        mPresenter.useUrl(mUri);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();

        super.onDestroy();
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
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void enableRefreshBySwipe(boolean enable) {
        mRefreshLayout.setEnabled(enable);
    }

    private void showToast(@NonNull String toast) {
        Snackbar.make(getView(), toast, Snackbar.LENGTH_SHORT);
    }
}
