package com.munch.browser.suggest;

import com.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SuggestActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract SuggestFragment suggestFragment();
}
