package com.munch.mvp;

public interface MvpContract {
    interface View extends MvpView {

    }

    interface Presenter extends MvpPresenter<View> {

    }

    abstract class SimplePresenter extends MvpPresenter.Simple<View> {

    }
}
