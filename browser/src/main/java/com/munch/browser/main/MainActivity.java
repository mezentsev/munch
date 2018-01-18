package com.munch.browser.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.munch.browser.R;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Inject
    MainFragment mMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);
        FragmentManager mFragmentManager = getSupportFragmentManager();

        MainFragment mainFragment = (MainFragment) mFragmentManager
                .findFragmentById(R.id.munch_main_container);

        if (mainFragment == null) {
            mainFragment = mMainFragment;

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
