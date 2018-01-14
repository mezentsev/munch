package com.munch.webview.di;

import com.munch.webview.MunchWebContract;
import com.munch.webview.presentation.MunchWebPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link MunchWebPresenter}.
 */
@Module
public abstract class MunchWebModule {
    @Binds
    abstract MunchWebContract.Presenter munchWebPresenter(MunchWebPresenter presenter);
}
