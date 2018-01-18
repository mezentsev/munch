package com.munch.browser.suggest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.munch.browser.R;
import com.munch.browser.callbacks.StaticOmniboxListener;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SuggestActivity extends DaggerAppCompatActivity
        implements StaticOmniboxListener {

    @Inject
    SuggestFragment mSuggestFragment;

    @Inject
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.munch_browser_main_activity);
        FragmentManager mFragmentManager = getSupportFragmentManager();

        SuggestFragment suggestFragment = (SuggestFragment) mFragmentManager
                .findFragmentById(R.id.munch_main_container);

        if (suggestFragment == null) {
            suggestFragment = mSuggestFragment;

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.munch_main_container, suggestFragment)
                    .commit();
        }
    }

    @Override
    public void onOmniboxClick() {
        Toast.makeText(mContext, "onOmniboxClick", Toast.LENGTH_SHORT).show();
    }
}
