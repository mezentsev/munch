package pro.mezentsev.munch.browser.di;

import pro.mezentsev.munch.browser.history.HistoryRepository;
import pro.mezentsev.munch.browser.web.MunchWebContract;
import pro.mezentsev.munch.browser.web.presentation.MunchWebPresenter;
import pro.mezentsev.munch.mvp.ActivityScoped;
import pro.mezentsev.munch.webview.WebContract;

import dagger.Module;
import dagger.Provides;
import pro.mezentsev.munch.browser.history.HistoryRepository;
import pro.mezentsev.munch.browser.web.presentation.MunchWebPresenter;

@Module
public abstract class MunchWebActivityModule {
    @ActivityScoped
    @Provides
    static MunchWebContract.Presenter provideWebPresenter(WebContract.View webView,
                                                          HistoryRepository historyRepository) {
        return new MunchWebPresenter(webView, historyRepository);
    }
}
