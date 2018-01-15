package com.munch.webview.di;

import com.munch.webview.WebContract;
import com.munch.webview.presentation.WebPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link WebPresenter}.
 */
@Module
public abstract class WebModule {
    @Binds
    abstract WebContract.Presenter munchWebPresenter(WebPresenter presenter);
}
