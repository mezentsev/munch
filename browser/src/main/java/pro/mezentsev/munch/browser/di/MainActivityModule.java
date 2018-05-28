package pro.mezentsev.munch.browser.di;

import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;
import pro.mezentsev.munch.browser.bookmarks.BookmarksRepository;
import pro.mezentsev.munch.browser.bookmarks.presentation.BookmarksPresenter;
import pro.mezentsev.munch.browser.bookmarks.view.BookmarksFragment;
import pro.mezentsev.munch.browser.history.HistoryContract;
import pro.mezentsev.munch.browser.history.HistoryRepository;
import pro.mezentsev.munch.browser.history.presentation.HistoryPresenter;
import pro.mezentsev.munch.browser.history.view.HistoryFragment;
import pro.mezentsev.munch.browser.main.MainFragment;
import pro.mezentsev.munch.browser.utils.MainThreadExecutor;
import pro.mezentsev.munch.mvp.ActivityScoped;
import pro.mezentsev.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import pro.mezentsev.munch.browser.bookmarks.BookmarksContract;
import pro.mezentsev.munch.browser.bookmarks.BookmarksRepository;
import pro.mezentsev.munch.browser.bookmarks.view.BookmarksFragment;
import pro.mezentsev.munch.browser.history.HistoryContract;
import pro.mezentsev.munch.browser.history.HistoryRepository;
import pro.mezentsev.munch.browser.history.presentation.HistoryPresenter;
import pro.mezentsev.munch.browser.history.view.HistoryFragment;
import pro.mezentsev.munch.browser.utils.MainThreadExecutor;

@Module
public abstract class MainActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract MainFragment mainFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BookmarksFragment bookmarksFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract HistoryFragment historyFragment();

    @ActivityScoped
    @Provides
    static BookmarksContract.Presenter provideBookmarkPresenter(BookmarksRepository bookmarksRepository,
                                                                MainThreadExecutor mainThreadExecutor) {
        return new BookmarksPresenter(bookmarksRepository, mainThreadExecutor);
    }

    @ActivityScoped
    @Provides
    static HistoryContract.Presenter provideHistoryPresenter(HistoryRepository historyRepository,
                                                             MainThreadExecutor mainThreadExecutor) {
        return new HistoryPresenter(historyRepository, mainThreadExecutor);
    }
}
