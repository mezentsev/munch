package com.munch.browser.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.munch.browser.R;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @NonNull
    private final String TAG = "[MNCH:Main]";

    private int backPressedCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);

        if (savedInstanceState == null) {
            FragmentManager mFragmentManager = getSupportFragmentManager();

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, new MainFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        ++backPressedCounter;
        Log.d(TAG, "onBackPressed " + backPressedCounter + " times.");
    }
}
