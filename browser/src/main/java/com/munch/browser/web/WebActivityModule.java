package com.munch.browser.web;

import com.munch.browser.web.view.WebActivity;
import com.munch.browser.web.view.WebFragment;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.munch.browser.web.view.WebActivity.EXTRA_URI;

@Module
public abstract class WebActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract WebFragment webFragment();

    @ActivityScoped
    @Provides
    static String provideUri(WebActivity activity) {
        return activity.getIntent().getStringExtra(EXTRA_URI);
    }
}
