package com.munch.browser.suggest;

import com.munch.browser.mvp.MvpPresenter;
import com.munch.browser.mvp.MvpView;

public interface IOmniboxContract {
    interface IOmniboxView extends MvpView {

    }

    interface IOmniboxPresenter extends MvpPresenter<IOmniboxView> {

    }
}
