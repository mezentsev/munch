package pro.mezentsev.munch.browser.di;

import pro.mezentsev.munch.browser.history.HistoryContract;
import pro.mezentsev.munch.browser.history.HistoryRepository;
import pro.mezentsev.munch.browser.history.presentation.HistoryPresenter;
import pro.mezentsev.munch.browser.history.view.HistoryFragment;
import pro.mezentsev.munch.browser.utils.MainThreadExecutor;
import pro.mezentsev.munch.mvp.ActivityScoped;
import pro.mezentsev.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import pro.mezentsev.munch.browser.history.presentation.HistoryPresenter;
import pro.mezentsev.munch.browser.history.view.HistoryFragment;

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
