package com.munch.browser.web;

import com.munch.browser.web.presentation.WebPresenter;
import com.munch.browser.web.view.WebActivity;
import com.munch.browser.web.view.WebFragment;
import com.munch.history.model.HistoryRepository;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.munch.browser.web.view.WebActivity.EXTRA_URI;

@Module
public abstract class WebModule {
    @Singleton
    @Provides
    static WebActivityContract.Presenter provideWebPresenter(HistoryRepository historyRepository) {
        return new WebPresenter(historyRepository);
    }
}
