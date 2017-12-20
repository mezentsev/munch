package com.munch.browser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.munch.browser.callbacks.StaticOmniboxCallback;

public class MunchActivity extends FragmentActivity implements StaticOmniboxCallback {
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
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No back activities", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStaticOmniboxClick() {
        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "Clicked static omnibox", Toast.LENGTH_SHORT).show();
        }

        SuggestFragment suggestFragment = SuggestFragment.newInstance();

        mFragmentManager
                .beginTransaction()
                .replace(R.id.munch_main_container, suggestFragment)
                .addToBackStack(SuggestFragment.class.getName())
                .commit();
    }
}
