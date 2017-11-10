package com.munch.browser.mvp;

import android.support.annotation.NonNull;

public interface MvpPresenter<V extends MvpView> {
    void onCreate();

    void onFirstAttachView();

    void attachView(@NonNull V view);

    void detachView();

    void onDestroy();
}
