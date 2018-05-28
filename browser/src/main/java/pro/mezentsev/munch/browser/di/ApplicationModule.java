package pro.mezentsev.munch.browser.di;

import android.app.Application;
import android.content.Context;

import pro.mezentsev.munch.mvp.ActivityScoped;
import pro.mezentsev.munch.webview.WebContract;
import pro.mezentsev.munch.webview.MunchWebView;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApplicationModule {
    @Binds
    abstract Context bindContext(Application application);

    @ActivityScoped
    @Binds
    abstract WebContract.View bindWebView(MunchWebView webView);
}

