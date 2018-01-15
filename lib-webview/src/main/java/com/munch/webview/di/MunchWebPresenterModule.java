package com.munch.webview.di;

import com.munch.mvp.ActivityScoped;
import com.munch.mvp.FragmentScoped;
import com.munch.webview.WebContract;
import com.munch.webview.presentation.MunchWebPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link MunchWebPresenter}.
 */
@Module
public abstract class MunchWebPresenterModule {

    @ActivityScoped
    @Binds
    abstract WebContract.Presenter munchWebPresenter(MunchWebPresenter presenter);
}
