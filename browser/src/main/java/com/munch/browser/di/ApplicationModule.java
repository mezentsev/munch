package com.munch.browser.di;

import android.app.Application;
import android.content.Context;

import com.munch.webview.WebContract;
import com.munch.webview.MunchWebView;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {
    @Binds
    abstract Context bindContext(Application application);

    @Singleton
    @Binds
    abstract WebContract.View bindWebView(MunchWebView webView);
}

