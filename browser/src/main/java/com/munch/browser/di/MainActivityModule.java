package com.munch.browser.di;

import com.munch.browser.bookmarks.BookmarksContract;
import com.munch.browser.bookmarks.BookmarksRepository;
import com.munch.browser.bookmarks.presentation.BookmarksPresenter;
import com.munch.browser.bookmarks.view.BookmarksFragment;
import com.munch.browser.history.HistoryContract;
import com.munch.browser.history.HistoryRepository;
import com.munch.browser.history.presentation.HistoryPresenter;
import com.munch.browser.history.view.HistoryFragment;
import com.munch.browser.main.MainFragment;
import com.munch.browser.utils.MainThreadExecutor;
import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

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
