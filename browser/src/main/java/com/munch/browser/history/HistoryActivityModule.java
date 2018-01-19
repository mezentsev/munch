package com.munch.browser.history;

import com.munch.browser.history.presentation.HistoryPresenter;
import com.munch.browser.history.view.HistoryFragment;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryFragment historyFragment();

    @ActivityScoped
    @Binds
    abstract HistoryContract.Presenter historyPresenter(HistoryPresenter historyPresenter);
}
