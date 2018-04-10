package com.munch.browser.di;

import com.munch.browser.history.HistoryActivityModule;
import com.munch.browser.history.view.HistoryActivity;
import com.munch.browser.main.MainActivityModule;
import com.munch.browser.main.MainActivity;
import com.munch.browser.suggest.SuggestActivity;
import com.munch.browser.suggest.SuggestActivityModule;
import com.munch.browser.web.view.WebActivity;
import com.munch.browser.web.WebActivityModule;
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
    @ContributesAndroidInjector(modules = WebActivityModule.class)
    abstract WebActivity webActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = HistoryActivityModule.class)
    abstract HistoryActivity historyActivity();
}
