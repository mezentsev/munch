package com.munch.browser.web;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.munch.browser.R;
import com.munch.browser.callbacks.StaticOmniboxListener;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class WebActivity extends DaggerAppCompatActivity {

    @NonNull
    public static final String EXTRA_URI = "URI";

    @Inject
    WebFragment mWebFragment;

    @Inject
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);
        FragmentManager mFragmentManager = getSupportFragmentManager();

        WebFragment webFragment = (WebFragment) mFragmentManager
                .findFragmentById(R.id.munch_main_container);

        if (webFragment == null) {
            webFragment = mWebFragment;

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, webFragment)
                    .commit();
        }
    }
}
