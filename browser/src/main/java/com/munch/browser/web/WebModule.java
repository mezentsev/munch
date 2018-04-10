package com.munch.browser.web;

import com.munch.browser.web.presentation.WebPresenter;
import com.munch.history.HistoryRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class WebModule {
    @Singleton
    @Provides
    static WebContract.Presenter provideWebPresenter(HistoryRepository historyRepository) {
        return new WebPresenter(historyRepository);
    }
}
