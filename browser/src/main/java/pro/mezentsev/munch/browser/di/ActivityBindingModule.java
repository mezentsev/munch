package pro.mezentsev.munch.browser.di;

import pro.mezentsev.munch.browser.history.view.HistoryActivity;
import pro.mezentsev.munch.browser.main.MainActivity;
import pro.mezentsev.munch.browser.suggest.view.SuggestActivity;
import pro.mezentsev.munch.browser.web.view.MunchWebActivity;
import pro.mezentsev.munch.mvp.ActivityScoped;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import pro.mezentsev.munch.browser.history.view.HistoryActivity;
import pro.mezentsev.munch.browser.main.MainActivity;
import pro.mezentsev.munch.browser.suggest.view.SuggestActivity;
import pro.mezentsev.munch.browser.web.view.MunchWebActivity;

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
