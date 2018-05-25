package com.munch.browser.history.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.munch.browser.R;
import com.munch.browser.base.view.BaseActivity;

import javax.inject.Inject;

public class HistoryActivity extends BaseActivity {

    @NonNull
    private static final String TAG = "[MNCH:HistoryActivity]";

    @Inject
    HistoryFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.munch_browser_simple_activity);
        FragmentManager mFragmentManager = getSupportFragmentManager();

        HistoryFragment historyFragment = (HistoryFragment) mFragmentManager
                .findFragmentById(R.id.munch_main_container);

        if (historyFragment == null) {
            historyFragment = mHistoryFragment;

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, historyFragment)
                    .commit();
        }
    }
}
