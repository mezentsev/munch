package com.munch.browser.suggest;

import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;
import com.munch.suggest.model.GoSuggestInteractor;
import com.munch.suggest.model.SuggestInteractor;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SuggestActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract SuggestFragment suggestFragment();

    @ActivityScoped
    @Provides
    static SuggestInteractor.Factory provideSuggestInteractorFactory() {
        return new GoSuggestInteractor.Factory();
    }
}
