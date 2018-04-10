package com.munch.browser.web.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.munch.browser.R;
import com.munch.browser.main.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class WebActivity extends DaggerAppCompatActivity {

    @NonNull
    public static final String EXTRA_URI = "URI";

    @Inject
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);

        if (savedInstanceState == null) {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, new WebFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
