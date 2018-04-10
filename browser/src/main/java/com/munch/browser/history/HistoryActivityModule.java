package com.munch.browser.history;

import com.munch.browser.history.presentation.HistoryPresenter;
import com.munch.browser.history.view.HistoryFragment;
import com.munch.browser.utils.MainThreadExecutor;
import com.munch.history.HistoryRepository;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HistoryActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryFragment historyFragment();

    @ActivityScoped
    @Provides
    static HistoryContract.Presenter provideHistoryPresenter(HistoryRepository historyRepository,
                                                             MainThreadExecutor mainThreadExecutor) {
        return new HistoryPresenter(historyRepository, mainThreadExecutor);
    }
}
