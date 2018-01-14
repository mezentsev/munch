package com.munch.browser.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.munch.browser.R;
import com.munch.browser.callbacks.StaticOmniboxCallback;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements StaticOmniboxCallback {

    @NonNull
    private FragmentManager mFragmentManager;
    @Nullable
    private Fragment mMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            mMainFragment = MainFragment.newInstance();

            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.munch_main_container, mMainFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.popBackStackImmediate(
                SuggestFragment.class.getName(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE)) {
        }
    }

    @Override
    public void onStaticOmniboxClick() {
        SuggestFragment suggestFragment = SuggestFragment.newInstance();

        mFragmentManager
                .beginTransaction()
                .replace(R.id.munch_main_container, suggestFragment)
                .addToBackStack(SuggestFragment.class.getName())
                .commit();
    }
}
