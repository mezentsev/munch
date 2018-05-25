package com.munch.browser.di;

import com.munch.browser.history.view.HistoryActivity;
import com.munch.browser.main.MainActivity;
import com.munch.browser.suggest.view.SuggestActivity;
import com.munch.browser.web.view.MunchWebActivity;
import com.munch.mvp.ActivityScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity munchActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SuggestActivityModule.class)
    abstract SuggestActivity suggestActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MunchWebActivityModule.class)
    abstract MunchWebActivity webActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HistoryActivityModule.class)
    abstract HistoryActivity historyActivity();
}
