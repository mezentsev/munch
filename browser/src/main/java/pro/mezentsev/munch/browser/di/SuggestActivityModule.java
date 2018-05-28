package pro.mezentsev.munch.browser.di;

import pro.mezentsev.munch.browser.suggest.view.SuggestFragment;
import pro.mezentsev.munch.mvp.ActivityScoped;
import pro.mezentsev.munch.mvp.FragmentScoped;
import pro.mezentsev.munch.suggest.model.GoSuggestInteractor;
import pro.mezentsev.munch.suggest.model.SuggestInteractor;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import pro.mezentsev.munch.browser.suggest.view.SuggestFragment;

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
