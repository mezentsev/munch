package com.munch.browser.mvp;

public interface MvpContract {
    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }
}
