package com.munch.browser.web;

import com.munch.browser.web.presentation.MunchWebPresenter;
import com.munch.history.HistoryRepository;
import com.munch.mvp.ActivityScoped;
import com.munch.webview.WebContract;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MunchWebActivityModule {
    @ActivityScoped
    @Provides
    static MunchWebContract.Presenter provideWebPresenter(WebContract.View webView,
                                                          HistoryRepository historyRepository) {
        return new MunchWebPresenter(webView, historyRepository);
    }
}